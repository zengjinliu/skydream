package comskydream.cn.skydream.custom;

import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.web.service.sys.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/29 16:30
 */
@Component
@Slf4j
public class UserExport {

    @Autowired
    private SysUserService sysUserService;

    public void exportUser(OutputStream outputStream ,String filename) {
        Workbook wb = new XSSFWorkbook();
        createWorkBook(wb,filename);
        try {
            wb.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void createWorkBook(Workbook wb,String filename) {
        Sheet sheet = wb.createSheet(filename);
        //设置表头
        createTableHead(sheet);
        //设置表格内容
        createTableBody(sheet);
    }

    /**
     * 设置excel列表的表头
     * @param sheet 工作薄
     */
    private void createTableHead(Sheet sheet) {
        Row row = sheet.createRow(0);
        List<String> list = new ArrayList<>();
        list.add("用户id");
        list.add("姓名");
        list.add("电话");
        for (int i = 0; i <list.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(list.get(i));
        }
    }

    /**
     * 设置列表的表内容数据
     * @param sheet 工作薄
     */
    private void createTableBody(Sheet sheet){
        List<SysUser> list = sysUserService.list(new SysUser());
        for (int i = 0; i < list.size(); i++) {
            SysUser sysUser = list.get(i);
            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(sysUser.getUserId());
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(sysUser.getUsername());
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(sysUser.getPhone());
        }
    }


}
