package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Model.ReservationVO;

public class SalesExcel {

	public boolean xslxWriter(List<ReservationVO> list, String saveDir) {
		// ��ũ�� ����
		XSSFWorkbook workbook = new XSSFWorkbook();
		// ��ũ��Ʈ ����
		XSSFSheet sheet = workbook.createSheet();
		// �� ����
		XSSFRow row = sheet.createRow(0);
		// �� ����
		XSSFCell cell;
		// ��� ���� ����
		cell = row.createCell(0);
		cell.setCellValue("��ǰ�ڵ�");

		cell = row.createCell(1);
		cell.setCellValue("����");

		cell = row.createCell(2);
		cell.setCellValue("����");

		cell = row.createCell(3);
		cell.setCellValue("�� �ݾ�");

		// ����Ʈ�� size ��ŭ row�� ����
		ReservationVO vo;
		for (int rowIdx = 0; rowIdx < list.size(); rowIdx++) {
			vo = list.get(rowIdx);

			// �� ����
			row = sheet.createRow(rowIdx + 1);

			cell = row.createCell(0);
			cell.setCellValue(vo.getTotalPrCode());

			cell = row.createCell(1);
			cell.setCellValue(vo.getTotalAdult());

			cell = row.createCell(2);
			cell.setCellValue(vo.getTotalChild());

			cell = row.createCell(3);
			cell.setCellValue(vo.getTotalAllPrice());

		}

		String strReportPDFName = "Sales_" + System.currentTimeMillis() + ".xlsx";
		File file = new File(saveDir + "\\" + strReportPDFName);
		FileOutputStream fos = null;

		boolean saveSuccess;
		saveSuccess = false;
		try {
			fos = new FileOutputStream(file);
			if (fos != null) {
				workbook.write(fos);
				saveSuccess = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null)
					workbook.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return saveSuccess;
	}
}
