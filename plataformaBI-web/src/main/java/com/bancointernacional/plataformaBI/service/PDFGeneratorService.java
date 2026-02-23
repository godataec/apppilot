package com.bancointernacional.plataformaBI.service;


import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

@Service
public class PDFGeneratorService {

    private final SpringTemplateEngine templateEngine;

    public PDFGeneratorService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public ByteArrayOutputStream generatePdf(String idQuest, Map<String, Object> data, String document, String fileName) throws Exception {
        Context context = new Context();
        context.setVariables(data);
        String content = this.templateEngine.process(document, context);
        final PdfRendererBuilder builder = new PdfRendererBuilder();
        Document jsoupDoc = Jsoup.parse(content, "UTF-8");
        Elements questionElements = jsoupDoc.select(".text-wrapper");
        int questionCounter = 1;
        for (Element element : questionElements) {
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
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(content);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
