package com.bancointernacional.plataformaBI.controller.utility;

import com.bancointernacional.plataformaBI.service.PDFGeneratorService;
import com.bancointernacional.plataformaBI.service.report.*;
import com.bancointernacional.plataformaBI.util.SerializationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/appendix/{policyQuestionaryId}")
    public ResponseEntity<byte[]> generateAppendixPdf(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {
        try {
            logger.info("Solicitud de formulario Apendice 3 con id {}",policyQuestionaryId);
            Map<String, Object> data =SerializationUtil.convertToMap(appendixService.fullFillQuestionerData(policyQuestionaryId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, APPENDIX_FORM, APPENDIX_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario Apendice 3 con id {}",policyQuestionaryId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", APPENDIX_FORM_FILE_NAME);
            logger.info("Creacion de respuesta Apendice 3 con id {}",policyQuestionaryId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/covid/{policyQuestionaryId}")
    public ResponseEntity<byte[]> generateCovidPdf(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {
        try {
            logger.info("Solicitud de formulario Covid con id {}",policyQuestionaryId);
            Map<String, Object> data =SerializationUtil.convertToMap(covidFormService.fullFillQuestionerData(policyQuestionaryId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, COVID_FORM, COVID_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario Covid con id {}",policyQuestionaryId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", COVID_FORM_FILE_NAME);
            logger.info("Creacion de respuesta Covid con id {}",policyQuestionaryId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/crime/{policyQuestionaryId}")
    public ResponseEntity<byte[]> generateCrimePdf(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {
        try {
            logger.info("Solicitud de formulario Crime con id {}",policyQuestionaryId);
            Map<String, Object> data = SerializationUtil.convertToMap(crimeFormService.fullFillQuestionerData(policyQuestionaryId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, CRIME_FORM, CRIME_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario Crime con id {}",policyQuestionaryId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", CRIME_FORM_FILE_NAME);
            logger.info("Creacion de respuesta Crime con id {}",policyQuestionaryId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/pi/{policyQuestionaryId}")
    public ResponseEntity<byte[]> generatePIPdf(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {
        try {
            logger.info("Solicitud de formulario PI con id {}",policyQuestionaryId);
            Map<String, Object> data = SerializationUtil.convertToMap(piQuestionerService.fullFillQuestionerData(policyQuestionaryId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, PI_FORM, PI_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario PI con id {}",policyQuestionaryId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", PI_FORM_FILE_NAME);
            logger.info("Creacion de respuesta PI con id {}",policyQuestionaryId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/cyberlock/{policyQuestionaryId}")
    public ResponseEntity<byte[]> generateCyberLockPdf(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {
        try {
            logger.info("Solicitud de formulario CyberLock con id {}",policyQuestionaryId);
            Map<String, Object> data = SerializationUtil.convertToMap(cyberLockTonProporsalService.fullFillQuestionerData(policyQuestionaryId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, CYBERLOCKPROP_FORM, CYBERLOCKPROP_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario convertToMap con id {}",policyQuestionaryId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", CYBERLOCKPROP_FORM_FILE_NAME);
            logger.info("Creacion de respuesta convertToMap con id {}",policyQuestionaryId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/cyberrisk/{policyQuestionaryId}")
    public  ResponseEntity<byte[]>  generateCyberRiskPdf(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {
        try {
            logger.info("Solicitud de formulario CyberRisk con id {}",policyQuestionaryId);
            Map<String, Object> data = SerializationUtil.convertToMap(cyberRiskService.fullFillQuestionerData(policyQuestionaryId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, CYBERRISK_FORM, CYBERRISK_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario CyberRisk con id {}",policyQuestionaryId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", CYBERRISK_FORM_FILE_NAME);
            logger.info("Creacion de respuesta CyberRisk con id {}",policyQuestionaryId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/cyberriskcom/{policyQuestionaryId}")
    public  ResponseEntity<byte[]>  generateCyberRiskComPdf(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {
        try {
            logger.info("Solicitud de formulario CyberRisk Complementario con id {}",policyQuestionaryId);
            Map<String, Object> data =SerializationUtil.convertToMap(cyberRiskComplementaryService.fullFillQuestionerData(policyQuestionaryId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, CYBERRISKCOM_FORM, CYBERRISKCOM_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario CyberRisk Complementario con id {}",policyQuestionaryId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", CYBERRISKCOM_FORM_FILE_NAME);
            logger.info("Creacion de respuesta CyberRisk Complementario con id {}",policyQuestionaryId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/cyberriskp/{policyQuestionaryId}")
    public ResponseEntity<byte[]> generateCyberRiskPPdf(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {
        try {
            logger.info("Solicitud de formulario CyberRisk Inf Personal con id {}",policyQuestionaryId);
            Map<String, Object> data = SerializationUtil.convertToMap(cyberRiskPersonalService.fullFillQuestionerData(policyQuestionaryId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, CYBERRISKP_FORM, CYBERRISKP_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario CyberRisk Inf Personal con id {}",policyQuestionaryId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", CYBERRISKP_FORM_FILE_NAME);
            logger.info("Creacion de respuesta CyberRisk Inf Personal con id {}",policyQuestionaryId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }

    }

    @GetMapping("/ransomware/{policyQuestionaryId}")
    public ResponseEntity<byte[]> generateRansomwarePdf(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {
        try {
            logger.info("Solicitud de formulario Ransomware con id {}",policyQuestionaryId);
            Map<String, Object> data = SerializationUtil.convertToMap(ransomwareFormService.fullFillQuestionerData(policyQuestionaryId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, RAN_FORM, RAN_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario Ransomware con id {}",policyQuestionaryId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", RAN_FORM_FILE_NAME);
            logger.info("Creacion de respuesta Ransomware con id {}",policyQuestionaryId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/willis/{policyQuestionaryId}")
    public ResponseEntity<byte[]>   generateWillisPdf(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {

        try {
            logger.info("Solicitud de formulario Willis con id {}",policyQuestionaryId);
            Map<String, Object> data = SerializationUtil.convertToMap(willisFormService.fullFillQuestionerData(policyQuestionaryId));
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, WILLIS_FORM, WILLIS_FORM_FILE_NAME).toByteArray();
            logger.info("Creacion de formulario Willis con id {}",policyQuestionaryId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", WILLIS_FORM_FILE_NAME);
            logger.info("Creacion de respuesta Willis con id {}",policyQuestionaryId);
            System.out.println(pdfBytes);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("{policyQuestionaryId}")
    public ResponseEntity<byte[]>    generateReportById(@PathVariable(name = "policyQuestionaryId") String policyQuestionaryId) throws Exception {
        try {
            Map<String, Object> data = SerializationUtil.convertToMap(willisFormService.createSampleForm());
            byte[] pdfBytes = pdfGeneratorService.generatePdf(policyQuestionaryId, data, WILLIS_FORM, WILLIS_FORM_FILE_NAME).toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", WILLIS_FORM_FILE_NAME);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }
}
