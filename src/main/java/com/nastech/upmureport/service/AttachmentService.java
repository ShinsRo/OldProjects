package com.nastech.upmureport.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.nastech.upmureport.domain.dto.AttachmentDto;
import com.nastech.upmureport.domain.dto.PfileDto;
import com.nastech.upmureport.domain.entity.Attachment;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Pfile;
import com.nastech.upmureport.domain.entity.support.LogState;
import com.nastech.upmureport.domain.repository.AttachmentRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.support.Utils;

@Service
public class AttachmentService {

	private final String UPLOAD_PATH = "C:\\\\Users\\\\nastech\\\\Desktop\\\\attachment";
	private final String PREFIX_URL = "localhost.com";
	private static final Log LOG = LogFactory.getLog(AttachmentService.class);

	AttachmentRepository attachmentRepository;
	PdirRepository pdirRepository;
	PLogService pLogService;

	public AttachmentService(AttachmentRepository attachmentRepository, PdirRepository pdirRepository, PLogService pLogService) {
		this.attachmentRepository = attachmentRepository;
		this.pdirRepository = pdirRepository;
		this.pLogService = pLogService;
	}

	// 첨부파일 저장
	public AttachmentDto.AttachmentResDto saveAttachment(MultipartFile file, AttachmentDto.AttachmentReqDto attachmentReqDto) throws Exception {

		File destinationFile = saveFile(file);
		String fileName = file.getOriginalFilename();
		Pdir pdir = pdirRepository.findById(Utils.StrToBigInt(attachmentReqDto.getPdir())).get();

		Attachment attachment = buildAttachment(destinationFile, fileName, pdir, attachmentReqDto, file);
		
		LOG.info(attachment.toString());
		
		try{			
			attachment = attachmentRepository.save(attachment);
			pLogService.createAttachmentLog(attachment, LogState.CREATE);
		} catch (Exception e) {
			throw new Exception();
		}			

		return attachment2AttachmentResDto(attachment);
	}

	// 첨부파일 리스트 가져오기
	public List<AttachmentDto.AttachmentResDto> getAttachment(BigInteger pdirId) {

		Pdir pdir = pdirRepository.findById(pdirId).get();
		List<Attachment> attachments = attachmentRepository.findByDid(pdir);
		List<AttachmentDto.AttachmentResDto> attachmentResDtos = attachments2AttachmentResDtos(attachments);

		return attachmentResDtos;
	}
	
	// 첨부파일 삭제
	public List<AttachmentDto.AttachmentResDto> deleteAttachment(String attachmentId) {
		Attachment attachment = attachmentRepository.findById(Utils.StrToBigInt(attachmentId)).get();
		
		attachment.deleteAttachment();
		
		try{			
			attachment = attachmentRepository.save(attachment);
			pLogService.createAttachmentLog(attachment, LogState.DELETE);
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}	
		
		return getAttachment(attachment.getPdir().getDid());
	}
	
	// 첨부파일 하나 다운로드
	public ResponseEntity<Resource> downloadAttachment(String attachmentId, HttpServletResponse resp) throws Exception {
		
		Attachment attachment = attachmentRepository.findById(Utils.StrToBigInt(attachmentId)).get();
		
		Path filePath = Paths.get(attachment.getLocalPath()).normalize();
		
		Resource resource = new UrlResource(filePath.toUri());
		
		
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	
	// 첨부파일 여러개 다운로드 zip 형식 
	public ResponseEntity<byte[]> downloadAttachmentGroup(List<String> attachmentIds) throws Exception {		

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
		ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
		List<Attachment> attachments = new ArrayList<Attachment>();
				
		attachmentIds.forEach(attachmentId -> {
			Attachment attachment = attachmentRepository.findById(Utils.StrToBigInt(attachmentId)).get();
			attachments.add(attachment);
		});	
		
		attachments.forEach(attachment -> {
			try {
				File fileToZip = new File(attachment.getLocalPath());
				FileInputStream fileInputStream = new FileInputStream(fileToZip);
	            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
	            zipOutputStream.putNextEntry(zipEntry);
	            
	            IOUtils.copy(fileInputStream, zipOutputStream);
	            
	            fileInputStream.close();
	            zipOutputStream.closeEntry();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		if (zipOutputStream != null)
	    {
	      zipOutputStream.finish();
	      zipOutputStream.flush();
	      IOUtils.closeQuietly(zipOutputStream);
	    }
	    IOUtils.closeQuietly(bufferedOutputStream);
	    IOUtils.closeQuietly(byteArrayOutputStream);		
				
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"download.zip\"")
                .body(byteArrayOutputStream.toByteArray());
	}
	
	// 첨부파일 이동
	public List<AttachmentDto.AttachmentResDto> moveAttachment(String attachmentId, String pdirId) {
		
		Attachment attachment = attachmentRepository.findById(Utils.StrToBigInt(attachmentId)).get();
		Pdir pdir = pdirRepository.findByDidAndDflagFalse(Utils.StrToBigInt(pdirId));
		
		Pdir originPdir = attachment.getPdir();
		
		attachment.moveAttachment(pdir);
		pLogService.createAttachmentLog(attachment, LogState.MOVE);
		
		return getAttachment(originPdir.getDid());
	}
	
	// 첨부파일 복사
	public AttachmentDto.AttachmentResDto copyAttachment(String attachmentId, String targetPdirId) {
		
		Attachment originAttachment = attachmentRepository.findById(Utils.StrToBigInt(attachmentId)).get();
		
		Pdir targetPdir = pdirRepository.findByDidAndDflagFalse(Utils.StrToBigInt(targetPdirId));
		
		Attachment copyAttachment = Attachment.builder().pdir(targetPdir)
				.name(originAttachment.getName())
				.coment(originAttachment.getComent())
				.contentType(originAttachment.getContentType())
				.volume(originAttachment.getVolume())
				.localPath(originAttachment.getLocalPath())
				.deleteFlag(false)
				.newDate(LocalDateTime.now())
				.build();		
		
		pLogService.createAttachmentLog(copyAttachment, LogState.COPY);
		
		return attachment2AttachmentResDto(attachmentRepository.save(copyAttachment));
	}
	
	
	

	private Attachment buildAttachment(File destinationFile, String fileName, Pdir pdir,AttachmentDto.AttachmentReqDto attachmentReqDto, MultipartFile file) {
		return Attachment.builder()
				.name(fileName)
				.url(PREFIX_URL + fileName)
				.localPath(destinationFile.getPath())
				.volume(destinationFile.length())
				.pdir(pdir)
				.coment(attachmentReqDto.getComent())
				.contentType(file.getContentType())
				.newDate(LocalDateTime.now())
				.deleteFlag(false).build();
	}

	private File saveFile(MultipartFile file) throws Exception{
		
		// 유니크 값 생성 
		UUID uid = UUID.randomUUID();
		
		String fileName = uid.toString() + "_" +file.getOriginalFilename();
		
		//파일을 저장할 폴더 생성(년 월 일 기준)
		String savedPath = calcPath(UPLOAD_PATH);
		
		File destinationFile = new File(UPLOAD_PATH + savedPath,fileName);
		
		file.transferTo(destinationFile);
		
		LOG.info(file.getContentType());
		
				
		return destinationFile;
	}
	


	private List<AttachmentDto.AttachmentResDto> attachments2AttachmentResDtos(List<Attachment> attachments) {
		List<AttachmentDto.AttachmentResDto> attachmentResDtos = new ArrayList<AttachmentDto.AttachmentResDto>();

		attachments.forEach(attachment -> {
			AttachmentDto.AttachmentResDto attachmentResDto = AttachmentDto.AttachmentResDto.builder()
					.attachmentId(attachment.getAttachmentId()).attachmentName(attachment.getName())
					.volume(attachment.getVolume()).newDate(attachment.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
					.coment(attachment.getComent())
					.contentType(attachment.getContentType()).build();

			attachmentResDtos.add(attachmentResDto);
		});

		return attachmentResDtos;
	}

	private AttachmentDto.AttachmentResDto attachment2AttachmentResDto(Attachment attachment) {

		AttachmentDto.AttachmentResDto attachmentResDto = AttachmentDto.AttachmentResDto.builder()
				.attachmentId(attachment.getAttachmentId()).attachmentName(attachment.getName())
				.volume(attachment.getVolume())
				.newDate(attachment.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.coment(attachment.getComent())
				.contentType(attachment.getContentType()).build();

		return attachmentResDto;
	}

	// 폴더 생성 함수
	@SuppressWarnings("unused")
	private static String calcPath(String uploadPath) {

		Calendar cal = Calendar.getInstance();

		String yearPath = File.separator + cal.get(Calendar.YEAR);

		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);

		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		makeDir(uploadPath, yearPath, monthPath, datePath);

		LOG.info(datePath);

		return datePath;
	}// calcPath

	// 폴더 생성 함수
	private static void makeDir(String uploadPath, String... paths) {

		if (new File(uploadPath + paths[paths.length - 1]).exists()) {
			return;
		} // if

		for (String path : paths) {
			File dirPath = new File(uploadPath + path);

			if (!dirPath.exists()) {
				dirPath.mkdir();
			} // if

		} // for

	}// makeDir
}