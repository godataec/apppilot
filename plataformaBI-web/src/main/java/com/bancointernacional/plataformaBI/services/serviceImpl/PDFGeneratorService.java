package com.bancointernacional.plataformaBI.services.serviceImpl;

import com.bancointernacional.plataformaBI.services.serviceInterface.AnswerService;
import com.bancointernacional.plataformaBI.services.serviceInterface.OptionsQuestionService;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionService;
import com.lowagie.text.pdf.PdfWriter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import java.io.*;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import static com.bancointernacional.plataformaBI.constant.Report.*;

@Service
public class PDFGeneratorService {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    OptionsQuestionService optionsQuestionService;


    private final SpringTemplateEngine templateEngine;

    public PDFGeneratorService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /*public void generatePdfLEGACY(HttpServletResponse response, Map<String, Object> data) throws IOException, DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process("anexo3", context);
        document.open();
        com.lowagie.text.html.simpleparser.HTMLWorker htmlWorker = new com.lowagie.text.html.simpleparser.HTMLWorker(document);
        htmlWorker.parse(new StringReader(htmlContent));
        document.close();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=anexo3.pdf");
        response.setContentLength(baos.size());
        baos.writeTo(response.getOutputStream());
    }*/

    public ByteArrayOutputStream generatePdf(String idQuest, Map<String, Object> data, String document, String fileName) throws Exception {
        Context context = new Context();
        context.setVariables(data);
        String content = this.templateEngine.process(document, context);
        final PdfRendererBuilder builder = new PdfRendererBuilder();
        Document jsoupDoc = Jsoup.parse(content, "UTF-8");
        Elements questionElements = jsoupDoc.select(".text-wrapper");
        int questionCounter = 1;
        for (Element element : questionElements) {
            // Add explicit number span as first child
            element.prependChild(new Element("span")
                    .addClass("explicit-number")
                    .text(questionCounter + ".")
                    .attr("style", "font-weight: bold; margin-right: 0.5em;"));
            questionCounter++;
        }

        String modifiedHtml = jsoupDoc.html();

        String baseUri = new File("").toURI().toURL().toString();
        jsoupDoc.setBaseUri(baseUri);
        org.w3c.dom.Document w3cDoc = new W3CDom().fromJsoup(jsoupDoc);
        builder.withW3cDocument(w3cDoc, baseUri);
        builder.useFont(new File("fonts/calibri.ttf"), "Calibri");
        builder.useFont(new File("fonts/calibri-bold.ttf"), "Calibri Bold");
        builder.useDefaultPageSize(215.9f, 279.4f, PdfRendererBuilder.PageSizeUnits.MM);
        builder.useFastMode();
        return generatePdfWithFlyingSaucer(content);
    }


    public ByteArrayOutputStream generatePdfWithFlyingSaucer(String content) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(outputStream);

        return outputStream;
    }


    /*public void generatePdf(HttpServletResponse response, Map<String, Object> data, String document, String fileName) throws Exception {
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process(document, context);
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        try (OutputStream os = response.getOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();
        }
    }*/


    /*public void generatePdf(String idQuest, HttpServletResponse response, Map<String, Object> data, String document, String fileName) throws Exception {
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process(document, context);
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        try (OutputStream os = response.getOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();
        }*/




}
