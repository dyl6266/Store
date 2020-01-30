package com.dy.store.common.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dy.store.attach.domain.AttachDto;

@Component
public class AttachUtils {

	private final static String uploadPath = "C:" + File.separator + "upload" + File.separator
			+ CommonUtils.getCurrentTime().substring(2, 10);

	/**
	 * 이미지 파일 썸네일 생성
	 * 
	 * @param storedName - 저장 파일명
	 * @param extension - 파일 확장자
	 * @return boolean
	 */
	private static boolean makeThumbnail(String storedName, String extension) {

		File target = new File(uploadPath, storedName);
		try {
			/* 원본 이미지에 대한 메모리상의 이미지를 의미하는 인스턴스 */
			BufferedImage sourceImage = ImageIO.read(target);

			/* 썸네일의 너비와 높이 */
			int tWidth = 450, tHeight = 270;

			/* 원본 이미지의 너비와 높이 */
			int oWidth = sourceImage.getWidth();
			int oHeight = sourceImage.getHeight();

			/* 늘어날 길이를 계산하여 패딩 */
			int padding = 0;
			if (oWidth > oHeight) {
				padding = (int) (Math.abs((tHeight * oWidth / (double) tWidth) - oHeight) / 2d);
			} else {
				padding = (int) (Math.abs((tWidth * oHeight / (double) tHeight) - oWidth) / 2d);
			}

			/* 주어진 색상으로 이미지 가장자리 주위에 패딩을 적용하여 추가 패딩 공간을 채워주는 메소드 (상/하/좌/우 동일하게 적용됨) */
			sourceImage = Scalr.pad(sourceImage, padding, Color.BLACK, Scalr.OP_ANTIALIAS);

			/* pad() 메소드로 변경된 이미지 사이즈 가져오기 */
			oWidth = sourceImage.getWidth();
			oHeight = sourceImage.getHeight();

			/* 썸네일 비율로 크롭할 크기 지정 (crop될 너비와 높이) */
			int cWidth = oWidth;
			int cHeight = (oWidth * tHeight) / tWidth;
			if (cHeight > oHeight) {
				cWidth = (oHeight * tWidth) / tHeight;
				cHeight = oHeight;
			}

			/*
			 * 늘어난 이미지의 중앙을 썸네일 비율로 크롭
			 * 
			 * 두 번째 인자 => crop할 좌상단의 X 좌표
			 * 세 번째 인자 => crop할 좌상단의 Y 좌표
			 */
			BufferedImage cropImage = Scalr.crop(sourceImage, (oWidth - cWidth) / 2, (oHeight - cHeight) / 2, cWidth,
					cHeight);
			/* 이미지 크기 조정 (썸네일 생성) */
			BufferedImage destImage = Scalr.resize(cropImage, tWidth, tHeight);

			/* 썸네일 이미지 경로 + 파일명 */
			String thumbnail = uploadPath + File.separator + "t_" + storedName;
			/* 썸네일 이미지 인스턴스 */
			target = new File(thumbnail);
			/* 디스크에 이미지 파일 생성 */
			ImageIO.write(destImage, extension.toUpperCase(), target);

		} catch (IOException e) {
			return false;

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * 업로드할 파일의 정보를 담은 리스트 반환
	 * 
	 * @param files - 상품 이미지 파일
	 * @param code - 상품 코드
	 * @return 업로드할 파일 목록
	 */
	public static List<AttachDto> uploadFiles(MultipartFile[] files, String code) {

		/* 파일 목록 담을 비어있는 리스트 */
		List<AttachDto> attachList = new ArrayList<>();

		/* uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉터리를 포함한 모든 디렉터리를 생성 */
		File target = new File(uploadPath);
		if (target.exists() == false) {
			target.mkdir();
		}

		for (MultipartFile file : files) {
			/* 파일 확장자 */
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			/* 저장 파일명 */
			String storedName = CommonUtils.getRandomString() + "." + extension;
			/* 확장자 체크 */
			MediaType mediaType = MediaUtils.getMediaType(extension);
			if (ObjectUtils.isEmpty(mediaType) == true) {
				return Collections.emptyList();
			}

			try {
				/* uploadPath에 해당하는 경로에 storedName과 동일한 이름으로 파일 생성 */
				target = new File(uploadPath, storedName);
				FileCopyUtils.copy(file.getBytes(), target);

				/* 썸네일 이미지 생성 */
				boolean isCreated = makeThumbnail(storedName, extension);
				if (isCreated == false) {
					return Collections.emptyList();
				}

				/* 파일 정보를 AttachDto 인스턴스에 담아 리스트에 추가 */
				AttachDto attach = AttachDto.builder().code(code).originalName(file.getOriginalFilename())
						.storedName(storedName).size(file.getSize()).build();
				attachList.add(attach);

			} catch (IOException e) {
				return Collections.emptyList();

			} catch (Exception e) {
				return Collections.emptyList();
			}
		}

		return attachList;
	}

	/**
	 * 디스크에서 파일 삭제
	 * 
	 * @param storedName - 저장 파일명
	 * @param uploadedDate - 업로드한 날짜
	 * @return boolean
	 */
	public static boolean deleteFile(String storedName, String uploadedDate) {

		/* 삭제할 파일 경로 */
		String path = "C:" + File.separator + "upload" + File.separator + uploadedDate + File.separator;

		/* 원본 이미지, 썸네일 이미지 존재 여부 체크 */
		File original = new File(path + storedName);
		File thumbnail = new File(path + "t_" + storedName);
		if (original.exists() == false || thumbnail.exists() == false) {
			return false;
		}

		/* 원본, 썸네일 이미지 삭제 */
		if (original.delete() == false || thumbnail.delete() == false) {
			return false;
		}

		return true;
	}

}
