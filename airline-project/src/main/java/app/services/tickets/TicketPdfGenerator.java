package app.services.tickets;

import app.entities.Ticket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TicketPdfGenerator {

    private static final Path TICKET_PATH = Paths.get("airline-project", "src", "main", "resources");

    public String generatePdf(String pathToPdf, Ticket ticket){

        //Document из itextpdf не реализует интерфейс Closeable, поэтому его нужно закрыть вручную
        try {
            Path ticketPattern = Paths.get(ClassLoader.getSystemResource("public/img/TicketPattern.png").toURI());

            //Создание и открытие файла
            Rectangle pageSize = new Rectangle(1600, 900);
            Document document = new Document(pageSize);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathToPdf));
            document.open();

            //Добавление шаблона
            Image img = Image.getInstance(ticketPattern.toAbsolutePath().toString());
            img.scaleToFit(document.getPageSize());
            document.add(img);


            /*Params:
            llx – lower left x - нижний левый x
            lly – lower left y - нижний левый y
            urx – upper right x - верхний правый x
            ury – upper right y - верхний правый y
            по x и y как в математике вставляем прямоугольник с текстом на наш билет*/

            //Flight Number
            addContent(ticket.getFlightSeat().getFlight().getCode(),
                    writer, 400, 480, 700, 510);

            //Departure Airport
            addContent(ticket.getFlightSeat().getFlight().getFrom().getAirportCode().toString(),
                    writer, 400, 405, 700, 435);

            //Departure time
            addContent(ticket.getFlightSeat().getFlight().getDepartureDateTime().toString(),
                    writer, 120, 270, 700, 300);

            //Arrival Airport
            addContent(ticket.getFlightSeat().getFlight().getTo().getAirportCode().toString(),
                    writer, 850, 405, 1000, 435);

            //Arrival time
            addContent(ticket.getFlightSeat().getFlight().getArrivalDateTime().toString(),
                    writer, 610, 270, 1000, 300);

            //Passenger name
            addContent(ticket.getPassenger().getFirstName(),
                    writer, 1160, 415, 1300, 445);

            //Passenger surname
            addContent(ticket.getPassenger().getPassport().getMiddleName(),
                    writer, 1160, 285, 1300, 315);

            //Passport
            addContent(ticket.getPassenger().getPassport().getSerialNumberPassport(),
                    writer, 1160, 75, 1500, 195);

            //Seat Number
            addContent(ticket.getFlightSeat().getSeat().getSeatNumber(),
                    writer, 120, 75, 700, 195);

            //Ticket Price
            addContent(ticket.getFlightSeat().getFare().toString(),
                    writer, 610, 75, 1000, 195);

            document.close();

        } catch (DocumentException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return pathToPdf;
    }

    public void addContent(String content, PdfWriter writer, int llx, int lly, int urx, int ury){

        //Настройка шрифта
        Font flightDetailsFont = FontFactory.getFont(FontFactory.HELVETICA);
        flightDetailsFont.setColor(BaseColor.BLACK);
        flightDetailsFont.setSize(36);

        //Добавление контента в шаблон
        try {
            PdfContentByte canvas = writer.getDirectContent();
            ColumnText columnText = new ColumnText(canvas);
            columnText.setSimpleColumn(new Rectangle(llx, lly, urx, ury));
            columnText.addText(new Paragraph(content, flightDetailsFont));
            columnText.go();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    @Scheduled(fixedRate = 60000)
    public void deleteAllPdfFilesInDirectory() {
        try (Stream<Path> stream = Files.walk(TICKET_PATH)) {
            stream.filter(filter -> filter.toString().toLowerCase().endsWith(".pdf"))
                    .forEach(fo -> {
                        try {
                            Files.delete(fo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
