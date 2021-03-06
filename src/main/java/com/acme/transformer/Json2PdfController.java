package com.acme.transformer;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Map;

import org.alfresco.transform.exceptions.TransformException;
import org.alfresco.transformer.AbstractTransformerController;
import org.alfresco.transformer.probes.ProbeTestTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

/**
 * Controller for handling requests to the Json2Pdf T-Engine. The T-Engine takes
 * an input json file and transforms it into a pre-configured dummy pdf file.
 */
@Controller
public class Json2PdfController extends AbstractTransformerController {
	private static final Logger logger = LoggerFactory.getLogger(Json2PdfController.class);

	@Value("classpath:dummy.pdf")
	private Resource dummyPdf;

	public Json2PdfController() {
		logger.info("-------------------------------------------");
		logger.info(getTransformerName() + " is starting up");
		logger.info("-------------------------------------------");
	}

	/**
	 * Simple transform json -> pdf
	 * 
	 * @see <a href=
	 *      "https://github.com/Alfresco/alfresco-transform-core/blob/master/docs/Probes.md">Probes.md</a>
	 * @return A quick transform used to check the health of the T-Engine
	 */
	@Override
	public ProbeTestTransform getProbeTestTransform() {
		//
		return new ProbeTestTransform(this, "probe_test.json", "probe_test.pdf", 180, 20, 150, 1024, 1, 1) {
			@Override
			protected void executeTransformCommand(File sourceFile, File targetFile) {
				Map<String, String> transformOptions = Collections.singletonMap("language", "Spanish");
				transformImpl("json2pdf", "application/json", "application/pdf", transformOptions, sourceFile,
						targetFile);
			}
		};
	}

	@Override
	public String getTransformerName() {
		return "ACME JSON to PDF Transformer";
	}

	@Override
	public String version() {
		return getTransformerName() + " available";
	}

	/**
	 * The actual transformation code.
	 */
	@Override
	public void transformImpl(String transformName, String sourceMimetype, String targetMimetype,
			Map<String, String> transformOptions, File sourceFile, File targetFile) {

		logger.info("{}, {}, {}, {}, {}, {}", transformName, sourceMimetype, targetMimetype, transformOptions,
				sourceFile.getAbsolutePath(), targetFile.getAbsolutePath());

		try {
			Files.copy(dummyPdf.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new TransformException(INTERNAL_SERVER_ERROR.value(),
					"There was a problem during transformation: " + e.getMessage());
		}
	}
}
