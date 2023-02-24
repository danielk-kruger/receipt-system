package com.project.receiptsystem.docs;

import com.spire.pdf.PdfDocument;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;

public class PrinterService {

    private PrinterJob printJob;
    private PageFormat pgFormat;

    public PrinterService() {
        //Create a PrinterJob object which is initially associated with the default printer
        this.printJob = PrinterJob.getPrinterJob();

        // Create a PageFormat object and set it to a default size and orientation
        this.pgFormat = printJob.defaultPage();
    }

    public PrinterJob setupPrinter(PdfDocument pdfInvoice) {
        // call printer to render the page in the specified format
        printJob.setPrintable(pdfInvoice, this.pgFormat);

        //Return a copy of the Paper object associated with this PageFormat
        Paper pgPaper = pgFormat.getPaper();

        //Set the imageable area of this Paper
        pgPaper.setImageableArea(0, 0, pgFormat.getWidth(), pgFormat.getHeight());

        //Set the Paper object for this PageFormat
        pgFormat.setPaper(pgPaper);

        return this.printJob;
    }


}
