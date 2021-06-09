import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.apache.commons.math3.util.Precision;


import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.LinkedList;


public class PDFMaker {

    private int orderId;
    private String datePlaced;
    private double grossPrice = 0;
    private double netPrice = 0;
    private double taxPercent = 0;
    private LinkedList<String> productNames;
    private LinkedList<Double> productPrices;
    private String companyName;
    private String nip;
    private String firstName;
    private String secondName;

    public PDFMaker(int orderId, String datePlaced, double grossPrice, double netPrice, double taxPercent, LinkedList<String> productNames,
                    LinkedList<Double> productPrices, String companyName,
                    String nip, String firstName, String secondName) throws FileNotFoundException {

        this.orderId = orderId;
        this.datePlaced = datePlaced;
        this.grossPrice = grossPrice;
        this.netPrice = netPrice;
        this.taxPercent = taxPercent;
        this.productNames = productNames;
        this.productPrices = productPrices;
        this.companyName = companyName;
        this.nip = nip;
        this.firstName = firstName;
        this.secondName = secondName;

        String path = "D:\\New folder\\Faktura.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        float col = 280f;
        float columnWidth[] = {col, col};

        Table table = new Table(columnWidth);

        table.setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE);
        table.addCell(new Cell().add("FAKTURA").setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setMarginTop(30f)
                .setMarginBottom(30f)
                .setFontSize(30f)
                .setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("DATABASE COMPANY\nul. Aghowska 17, Kraków\n 001122334455").setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(30f)
                .setMarginBottom(30f)
                .setBorder(Border.NO_BORDER)
                .setMarginRight(10f));

        float colWidth[] = {120,250,120,180};
        Table customerInfoTable = new Table(colWidth);

        customerInfoTable.addCell(new Cell(0,4).add("Informacje o kliencie").setBold());

        customerInfoTable.addCell(new Cell().add("Imie i nazwisko:").setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add("" + firstName + " " + secondName).setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add("Nazwa firmy:").setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add("" + companyName).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
        customerInfoTable.addCell(new Cell().add("NIP:").setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add("" + nip).setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add("Data zamówienia:").setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add("" + datePlaced).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));


        float itemInfoColWidth[] = {140, 140, 140, 140};
        Table itemInfoTable = new Table(itemInfoColWidth);

        itemInfoTable.addCell(new Cell().add("Nr").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
        itemInfoTable.addCell(new Cell().add("Nazwa produktu").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
        itemInfoTable.addCell(new Cell().add("Cena brutto").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT));
        itemInfoTable.addCell(new Cell().add("Cena netto").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT));



        for(int i = 0; i<productNames.size(); i++){
            itemInfoTable.addCell(new Cell().add("" + (i+1)));
            itemInfoTable.addCell(new Cell().add("" + productNames.get(i)));
            itemInfoTable.addCell(new Cell().add("" + productPrices.get(i)).setTextAlignment(TextAlignment.RIGHT));
            itemInfoTable.addCell(new Cell().add("" + Precision.round((productPrices.get(i) / (1.00 + (taxPercent*0.01))), 1)).setTextAlignment(TextAlignment.RIGHT));
        }
        itemInfoTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
        itemInfoTable.addCell(new Cell().add("Razem").setBorder(Border.NO_BORDER).setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT));
        itemInfoTable.addCell(new Cell().add("" + grossPrice).setBorder(Border.NO_BORDER).setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT));
        itemInfoTable.addCell(new Cell().add("" + Precision.round(netPrice, 1)).setBorder(Border.NO_BORDER).setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT));


        float lastColWidth[] = {140, 140, 140, 107};
        Table lastCol = new Table(lastColWidth);
        lastCol.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        lastCol.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        lastCol.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        lastCol.addCell(new Cell().add("Wystawiona przez:").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT));

        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(customerInfoTable);
        document.add(itemInfoTable);
        document.add(new Paragraph("\n"));
        document.add(lastCol);
        document.add(new Paragraph("................................").setTextAlignment(TextAlignment.RIGHT));


        document.close();
        System.out.println("faktura");

    }


}
