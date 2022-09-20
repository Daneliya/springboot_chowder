package com.xxl.poi.doc;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @Author: xxl
 * @Date: 2022/09/20 11:31
 */
public class WordUtil {

    /**
     * 读取模板
     *
     * @param response
     * @throws Exception
     */
    public static void operatorWord(HttpServletResponse response) throws Exception {
        //获得模板文件
        String path = "C:\\Users\\ek\\Desktop\\模板A.docx";
        InputStream docIS = new FileInputStream(path);
        //转成word
        CustomXWPFDocument document = new CustomXWPFDocument(docIS);
        //写入图片
        insertImage(document);
        //写入表格
        insertTable(document);
        //段落替换对象
        insertText(document);
        //把doc输出到输出流
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msword");
        //文件名
        String fileName = "考试成绩分析" + System.currentTimeMillis();
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
        ServletOutputStream responseOutputStream = response.getOutputStream();
        document.write(responseOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
        docIS.close();
    }

    /**
     * 写入图片在word中
     *
     * @param document
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void insertImage(CustomXWPFDocument document) throws IOException, InvalidFormatException {
        //图片
        FileInputStream in = new FileInputStream("D:\\文件\\插件\\背景图片\\193708-15779650287a6a.jpg");
        //段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            //获取到段落中的所有文本内容
            String text = paragraph.getText();
            //判断此段落中是否有需要进行替换的文本
            if (WordUtil.checkText(text)) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    //替换模板原来位置
                    String key = "${image}";
                    if (run.toString().indexOf(key) != -1) {
                        byte[] ba = new byte[in.available()];
                        int len = in.read(ba);
                        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(ba, 0, len);
                        //设置图片
                        document.addPictureData(byteInputStream, XWPFDocument.PICTURE_TYPE_PNG);
                        //创建一个word图片，并插入到文档中-->像素可改
                        document.createPicture(document.getAllPictures().size() - 1, 240, 240, paragraph);
                    }
                    break;
                }
                break;
            }
        }
    }

    public static void insertTable(CustomXWPFDocument document) {
        //创建表格接受参数-->外层list是行内层是列
        List<List<String>> tableList = new ArrayList();

        List<String> cells = new ArrayList<>();
        cells.add("1");
        cells.add("张三");
        cells.add("100");
        tableList.add(cells);

        List<String> cellList = new ArrayList<>();
        cellList.add("2");
        cellList.add("李四");
        cellList.add("10");
        tableList.add(cellList);
        //获取表格位置
        List<XWPFTable> tables = document.getTables();

        document.createTable(tableList.size() - 1, tableList.get(0).size());
        // 处理表头
        List<String> headList = new ArrayList();
        headList.add("序号");
        headList.add("姓名");
        headList.add("成绩");
        XWPFTable xwpfTable = tables.get(0);
        XWPFTableRow row = xwpfTable.getRow(0);
        List<XWPFTableCell> tableCellList = row.getTableCells();
        for (int j = 0; j < tableCellList.size(); j++) {
            XWPFTableCell cell = tableCellList.get(j);
            cell.setText(headList.get(j));
            //表格样式一致-->没有此段表格会默认左对齐
            //有此段会使表格格式一致
            CTTc cttc = cell.getCTTc();
            CTTcPr ctPr = cttc.addNewTcPr();
            ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
            cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        }
        WordUtil.insertTable(tables.get(0), tableList);
    }

    /**
     * 为表格插入数据，行数不够添加新行
     *
     * @param table     需要插入数据的表格
     * @param tableList 插入数据集合
     */
    public static void insertTable(XWPFTable table, List<List<String>> tableList) {
        //创建行,根据需要插入的数据添加新行，不处理表头
        for (int i = 1; i <= tableList.size(); i++) {
            table.createRow();
        }
        //遍历表格插入数据
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 1; i < rows.size(); i++) {
            XWPFTableRow newRow = table.getRow(i);
            List<XWPFTableCell> cells = newRow.getTableCells();
            // 行中每列内容
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                cell.setText(tableList.get(i - 1).get(j));
                //表格样式一致-->没有此段表格会默认左对齐
                //有此段会使表格格式一致
                CTTc cttc = cell.getCTTc();
                CTTcPr ctPr = cttc.addNewTcPr();
                ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
            }
        }
    }

    public static void insertText(CustomXWPFDocument document) {
        //声明替换模板对象
        Map textMap = new HashMap();
        textMap.put("${maxName}", "张三");
        textMap.put("${maxScore}", "100");
        textMap.put("${minName}", "李四");
        textMap.put("${minScore}", "10");
        //替换模板数据
        WordUtil.changeText(document, textMap);
    }

    /**
     * 替换段落文本
     *
     * @param document docx解析对象
     * @param textMap  需要替换的信息集合
     */
    public static void changeText(XWPFDocument document, Map<String, Object> textMap) {
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            //获取到段落中的所有文本内容
            String text = paragraph.getText();
            //判断此段落中是否有需要进行替换的文本
            if (checkText(text)) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    //替换模板原来位置
                    run.setText(changeValue(run.toString(), textMap), 0);
                }
            }
        }
    }

    /**
     * 判断文本中是否包含$
     *
     * @param text 文本
     * @return 包含返回true, 不包含返回false
     */
    public static boolean checkText(String text) {
        boolean check = false;
        if (text.indexOf("$") != -1) {
            check = true;
        }
        return check;
    }

    /**
     * 替换模板${}
     */
    private static String changeValue(String value, Map<String, Object> textMap) {
        Set<Map.Entry<String, Object>> textSets = textMap.entrySet();
        String valu = "";
        for (Map.Entry<String, Object> textSet : textSets) {
            // 匹配模板与替换值 格式${key}
            String key = textSet.getKey();
            if (value.contains(key)) {
                valu = textSet.getValue().toString();
            }
        }
        return valu;
    }

}
