package cn.itcast.serlvet;

import cn.itcast.pojo.Student;
import cn.itcast.service.StudentService;
import cn.itcast.service.impl.StudentServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/26
 * Time:17:45
 */
@WebServlet("/modelServlet")
public class ModelServlet extends HttpServlet {

    private StudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");
        if (method != null) {
            if ("outModeStudent".equals(method)) {
                outModeStudent(req,resp);
            } else if ("outAllStudent".equals(method)) {
                outAllStudent(req,resp);
            } else if ("addModeStudent".equals(method)) {
                try {
                    addModeStudent(req,resp);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void addModeStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException, FileUploadException {
        FileItemFactory factory = new DiskFileItemFactory(); // 处理上传文件的工厂
        // 文件上传核心工具类
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(10 * 1024 * 1024); // 单个文件大小限制
        upload.setSizeMax(50 * 1024 * 1024); // 总文件大小限制
        upload.setHeaderEncoding("UTF-8"); // 对中文文件编码处理

        if (ServletFileUpload.isMultipartContent(req)) { // 把request请求进行判断是否是文件上传表单  如果是会返回true 如果不是返回false
            List<FileItem> list = upload.parseRequest(req); // 进行解析 返回每一个表单项
            // 遍历
            for (FileItem item : list) {
                if (!item.isFormField()) { // 判断是否是文件上传表单项
                    List<Student> students = readExcel(item.getInputStream());// 如果是进行解析
                    for (int i = 0; i < students.size(); i++) {
                        Student student = students.get(i);
                        Integer stuNo = student.getStuNo();
                        Student studentById = studentService.findStudentById(stuNo);
                        if (studentById == null) {
                            studentService.insertStudent(student);
                        }
                    }

                }
            }
        }

        resp.sendRedirect("studentList");


    }

    private List<Student> readExcel(InputStream inputStream) {
        List<Student> stuList = new ArrayList<>();
        try (HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream)) {
            HSSFSheet sheet = hssfWorkbook.getSheet("student");
          //  System.out.println(sheet.getRow(0).getCell(0).getStringCellValue());
            int lastRowNum = sheet.getLastRowNum(); // 获取总行数 包括表头
            int physicalNumberOfCells = sheet.getRow(0).getPhysicalNumberOfCells(); // 获取一行中的总列数
            for (int i = 1; i < lastRowNum+1; i++) {
                Student student = new Student();
               // System.out.println(sheet.getRow(1).getCell(0).getStringCellValue());

                for (int j = 0; j < physicalNumberOfCells; j++) {
                    sheet.getRow(i).getCell(j).setCellType(Cell.CELL_TYPE_STRING); // 要先设置列的类型 不然会抛异常
                    if (j == 0) {
                        // System.out.println(sheet.getRow(0).getCell(0).getStringCellValue());
                        String stuNo = sheet.getRow(i).getCell(j).getStringCellValue(); // 获取学号
                        student.setStuNo(Integer.parseInt(stuNo));
                    } else if (j == 1) {
                        String name = sheet.getRow(i).getCell(j).getStringCellValue();
                        student.setName(name);
                    } else if (j == 2) {
                        String age = sheet.getRow(i).getCell(j).getStringCellValue();
                        student.setAge(Integer.parseInt(age));
                    } else if (j == 3) {
                        String sex = sheet.getRow(i).getCell(j).getStringCellValue();
                        if ("男".equals(sex)) {
                            student.setSex(1);
                        } else {
                            student.setSex(0);
                        }
                    }
                }
                stuList.add(student);
            }
            return stuList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    private void outAllStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Content-Type", "xls");
        // 设置要下载的名字
        String filename = "学生信息导出数据";
        resp.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "ISO8859-1") + ".xls");
        String[] title = {"学号","学生姓名","学生年龄","学生性别"};
        HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个工作簿
        HSSFSheet sheet = workbook.createSheet("student");
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < 4; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        /*HSSFCellStyle cellStyle = workbook.createCellStyle(); // 设置样式 3.17特有
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中*/
        List<Student> studentAll = studentService.findStudentAll();
        String sex = "";
        for (int i = 0 ; i < studentAll.size(); i++) {
            HSSFRow row1 = sheet.createRow(i+1);
            for (int j = 0; j < 4; j++) {
                HSSFCell cell = row1.createCell(j);
                // cell.setCellValue(studentAll.get(i).);
                if (j == 0) {
                    cell.setCellValue(studentAll.get(i).getStuNo());
                } else if (j == 1) {
                    cell.setCellValue(studentAll.get(i).getName());
                } else if (j == 2) {
                    cell.setCellValue(studentAll.get(i).getAge());
                } else if (j == 3) {
                    Integer sex1 = studentAll.get(i).getSex();
                    if (sex1 == 1) {
                        sex = "男";
                    } else {
                        sex = "女";
                    }
                    cell.setCellValue(sex);
                }
            }
        }



        ServletOutputStream out = resp.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
       // resp.sendRedirect("studentList");

    }

    private void outModeStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Content-Type", "xls");
        // 设置要下载的名字
        String filename = "学生信息管理批量导入模板";
        resp.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "ISO8859-1") + ".xls");
        String[] title = {"学号","学生姓名","学生年龄","学生性别"};
        HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个工作簿
        /*HSSFCellStyle cellStyle = workbook.createCellStyle(); // 设置样式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中*/

        HSSFSheet sheet = workbook.createSheet("student");
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < 4; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        ServletOutputStream out = resp.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
