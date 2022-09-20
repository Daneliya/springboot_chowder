package com.xxl.poi;

import com.xxl.poi.doc.CustomXWPFDocument;
import com.xxl.poi.doc.WordUtil;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xxl
 * @Date: 2022/09/20 14:32
 */

public class POITest {

    @Test
    public void test01() throws Exception {
        //获得模板文件
        String path = "C:\\Users\\ek\\Desktop\\模板A.docx";
        InputStream docIS = new FileInputStream(path);
        //转成word
        CustomXWPFDocument document = new CustomXWPFDocument();
        //写入图片
        //WordUtil.insertImage(document);
        //写入表格
        WordUtil.insertTable(document);
        //段落替换对象
        //WordUtil.insertText(document);
        //文件名
        String fileName = "考试成绩分析" + System.currentTimeMillis();
        FileOutputStream os = new FileOutputStream("C:\\Users\\ek\\Desktop\\导出模板B.docx");
        document.write(os);
        docIS.close();
    }

    @Test
    public void test02() throws Exception {
        //转成word
        CustomXWPFDocument document = new CustomXWPFDocument();
        //写入图片
        //WordUtil.insertImage(document);
        //写入表格
        insertTable(document);
        //段落替换对象
        //WordUtil.insertText(document);
        //文件名
        FileOutputStream os = new FileOutputStream("C:\\Users\\ek\\Desktop\\导出模板C.docx");
        document.write(os);
    }

    public static void insertTable(CustomXWPFDocument document) {
        //创建表格接受参数-->外层list是行内层是列
        List<List<String>> tableList = new ArrayList();

        List<String> cell1 = new ArrayList<>();
        cell1.add("1");
        cell1.add("一");
        cell1.add("2022.7.18");
        cell1.add("1-2");
        cell1.add("泌尿系统疾病总论");
        cell1.add("1班、6班");
        cell1.add("江苏路");
        cell1.add("义鸿");
        tableList.add(cell1);

        List<String> cell2 = new ArrayList<>();
        cell2.add("1");
        cell2.add("一");
        cell2.add("2022.7.18");
        cell2.add("1-2");
        cell2.add("泌尿系统疾病总论");
        cell2.add("1班、6班");
        cell2.add("江苏路");
        cell2.add("义鸿");
        tableList.add(cell2);


        // 处理表头
        List<String> headList = new ArrayList();
        headList.add("周次");
        headList.add("星期");
        headList.add("日期");
        headList.add("节次");
        headList.add("内容");
        headList.add("班次");
        headList.add("教室");
        headList.add("任课教师");
        document.createTable(1, headList.size());

        //获取表格位置
        List<XWPFTable> tables = document.getTables();
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
}
