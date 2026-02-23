package com.bancointernacional.plataformaBI.controllers;

import com.bancointernacional.plataformaBI.services.report.*;
import com.bancointernacional.plataformaBI.services.serviceImpl.PDFGeneratorService;
import com.bancointernacional.plataformaBI.util.SerializationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.bancointernacional.plataformaBI.constant.Report.*;

@RequestMapping("/report")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ReportController {

    private static final Logger logger = LogManager.getLogger(ReportController.class);

    private final PDFGeneratorService pdfGeneratorService;
    private final AppendixFormService appendixService;
    private final CovidFormService covidFormService;
    private final CrimeFormService crimeFormService;
    private final CyberLockTonProporsalService cyberLockTonProporsalService;
    private final CyberRiskComplementaryService cyberRiskComplementaryService;
    private final CyberRiskPersonalService cyberRiskPersonalService;
    private final CyberRiskService cyberRiskService;
    private final PiQuestionerService piQuestionerService;
    private final RansomwareFormService ransomwareFormService;
    private final WillisFormService willisFormService;

    @Autowired
    public ReportController(PDFGeneratorService pdfGeneratorService, AppendixFormService appendixService,
                            CovidFormService covidFormService, CrimeFormService crimeFormService,
                            CyberLockTonProporsalService cyberLockTonProporsalService,
                            CyberRiskComplementaryService cyberRiskComplementaryService, CyberRiskPersonalService cyberRiskPersonalService,
                            CyberRiskService cyberRiskService, PiQuestionerService piQuestionerService,
                            RansomwareFormService ransomwareFormService, WillisFormService willisFormService) {
        this.appendixService = appendixService;
        this.pdfGeneratorService = pdfGeneratorService;
        this.covidFormService = covidFormService;
        this.crimeFormService = crimeFormService;
        this.cyberLockTonProporsalService = cyberLockTonProporsalService;
        this.cyberRiskComplementaryService = cyberRiskComplementaryService;
        this.cyberRiskPersonalService = cyberRiskPersonalService;
        this.cyberRiskService = cyberRiskService;
        this.piQuestionerService = piQuestionerService;
        this.ransomwareFormService = ransomwareFormService;
        this.willisFormService = willisFormService;
    }

    @GetMapping("/appendix/{reportId}")
    public ResponseEntity<byte[]> generateAppendixPdf(HttpServletResponse response, @PathVariable(name = "reportId") String reportId) throws Exception {
        try {
            logger.info("Solicitud de formulario Apendice 3 con id {}",reportId);
            Map<String, Object> data =SerializationUtil.convertToMap(appendixService.fullFillQuestionerData(reportId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, APPENDIX_FORM, APPENDIX_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario Apendice 3 con id {}",reportId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", APPENDIX_FORM_FILE_NAME);
            logger.info("Creacion de respuesta Apendice 3 con id {}",reportId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/covid/{reportId}")
    public ResponseEntity<byte[]> generateCovidPdf(HttpServletResponse response, @PathVariable(name = "reportId") String reportId) throws Exception {
        try {
            logger.info("Solicitud de formulario Covid con id {}",reportId);
            Map<String, Object> data =SerializationUtil.convertToMap(covidFormService.fullFillQuestionerData(reportId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, COVID_FORM, COVID_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario Covid con id {}",reportId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", COVID_FORM_FILE_NAME);
            logger.info("Creacion de respuesta Covid con id {}",reportId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/crime/{reportId}")
    public ResponseEntity<byte[]> generateCrimePdf(HttpServletResponse response, @PathVariable(name = "reportId") String reportId) throws Exception {
        try {
            logger.info("Solicitud de formulario Crime con id {}",reportId);
            Map<String, Object> data = SerializationUtil.convertToMap(crimeFormService.fullFillQuestionerData(reportId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, CRIME_FORM, CRIME_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario Crime con id {}",reportId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", CRIME_FORM_FILE_NAME);
            logger.info("Creacion de respuesta Crime con id {}",reportId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/pi/{reportId}")
    public ResponseEntity<byte[]> generatePIPdf(HttpServletResponse response, @PathVariable(name = "reportId") String reportId) throws Exception {
        try {
            logger.info("Solicitud de formulario PI con id {}",reportId);
            Map<String, Object> data = SerializationUtil.convertToMap(piQuestionerService.fullFillQuestionerData(reportId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, PI_FORM, PI_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario PI con id {}",reportId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", PI_FORM_FILE_NAME);
            logger.info("Creacion de respuesta PI con id {}",reportId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/cyberlock/{reportId}")
    public ResponseEntity<byte[]> generateCyberLockPdf(HttpServletResponse response, @PathVariable(name = "reportId") String reportId) throws Exception {
        try {
            logger.info("Solicitud de formulario CyberLock con id {}",reportId);
            Map<String, Object> data = SerializationUtil.convertToMap(cyberLockTonProporsalService.fullFillQuestionerData(reportId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, CYBERLOCKPROP_FORM, CYBERLOCKPROP_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario convertToMap con id {}",reportId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", CYBERLOCKPROP_FORM_FILE_NAME);
            logger.info("Creacion de respuesta convertToMap con id {}",reportId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/cyberrisk/{reportId}")
    public  ResponseEntity<byte[]>  generateCyberRiskPdf(HttpServletResponse response, @PathVariable(name = "reportId") String reportId) throws Exception {
        try {
            logger.info("Solicitud de formulario CyberRisk con id {}",reportId);
            Map<String, Object> data = SerializationUtil.convertToMap(cyberRiskService.fullFillQuestionerData(reportId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, CYBERRISK_FORM, CYBERRISK_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario CyberRisk con id {}",reportId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", CYBERRISK_FORM_FILE_NAME);
            logger.info("Creacion de respuesta CyberRisk con id {}",reportId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/cyberriskcom/{reportId}")
    public  ResponseEntity<byte[]>  generateCyberRiskComPdf(@PathVariable(name = "reportId") String reportId) throws Exception {
        try {
            logger.info("Solicitud de formulario CyberRisk Complementario con id {}",reportId);
            Map<String, Object> data =SerializationUtil.convertToMap(cyberRiskComplementaryService.fullFillQuestionerData(reportId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, CYBERRISKCOM_FORM, CYBERRISKCOM_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario CyberRisk Complementario con id {}",reportId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", CYBERRISKCOM_FORM_FILE_NAME);
            logger.info("Creacion de respuesta CyberRisk Complementario con id {}",reportId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/cyberriskp/{reportId}")
    public ResponseEntity<byte[]> generateCyberRiskPPdf(@PathVariable(name = "reportId") String reportId) throws Exception {
        try {
            logger.info("Solicitud de formulario CyberRisk Inf Personal con id {}",reportId);
            Map<String, Object> data = SerializationUtil.convertToMap(cyberRiskPersonalService.fullFillQuestionerData(reportId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, CYBERRISKP_FORM, CYBERRISKP_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario CyberRisk Inf Personal con id {}",reportId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", CYBERRISKP_FORM_FILE_NAME);
            logger.info("Creacion de respuesta CyberRisk Inf Personal con id {}",reportId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }

    }

    @GetMapping("/ransomware/{reportId}")
    public ResponseEntity<byte[]> generateRansomwarePdf(@PathVariable(name = "reportId") String reportId) throws Exception {
        try {
            logger.info("Solicitud de formulario Ransomware con id {}",reportId);
            Map<String, Object> data = SerializationUtil.convertToMap(ransomwareFormService.fullFillQuestionerData(reportId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, RAN_FORM, RAN_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario Ransomware con id {}",reportId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", RAN_FORM_FILE_NAME);
            logger.info("Creacion de respuesta Ransomware con id {}",reportId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/willis/{reportId}")
    public ResponseEntity<byte[]>   generateWillisPdf(@PathVariable(name = "reportId") String reportId) throws Exception {

        try {
            logger.info("Solicitud de formulario Willis con id {}",reportId);
            Map<String, Object> data = SerializationUtil.convertToMap(willisFormService.fullFillQuestionerData(reportId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, WILLIS_FORM, WILLIS_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario Willis con id {}",reportId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", WILLIS_FORM_FILE_NAME);
            logger.info("Creacion de respuesta Willis con id {}",reportId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("{reportId}")
    public ResponseEntity<byte[]>    generateReportById(@PathVariable(name = "reportId") String reportId) throws Exception {
        try {
            Map<String, Object> data = SerializationUtil.convertToMap(willisFormService.createSampleForm());
            byte[] pdfBytes = pdfGeneratorService.generatePdf(reportId, data, WILLIS_FORM, WILLIS_FORM_FILE_NAME).toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", WILLIS_FORM_FILE_NAME);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any errors before committing the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }
}
