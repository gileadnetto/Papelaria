package com.example.gilead.tccpatrimonio;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FilePDFOperations {
    String currentDateTimeString = DateFormat.getDateInstance().format(new Date());


    public FilePDFOperations() {
    }

    public Boolean write(String fname,ArrayList<String> dados) {


        try {


            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.e("local",baseDir);

            //String fpath = "/sdcard/" + fname + ".pdf";
           // File file = new File(fpath);
            File file = new File(Environment.getExternalStorageDirectory(), fname + ".pdf");

            Log.e("--",file.toString());

            // If file does not exists, then create it
            if (! file.exists()) {
                file.createNewFile();
            }
            // step 1
            Document document = new Document(PageSize.A4);

            // step 2
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
            // step 3
            document.open();
            // step 4



            //TODO------------CABECALHO ---------------------

           Paragraph  paragraph = new Paragraph("BAZAR E PAPELARIA AMARELINHA DE IGUABA");
           paragraph.setAlignment(Element.ALIGN_CENTER);
           document.add(paragraph);

            paragraph = new Paragraph("MATHEUS PY BRAGA");
           paragraph.setAlignment(Element.ALIGN_CENTER);
           document.add(paragraph);


          //  paragraph = new Paragraph("SECRETARIA MUNICIPAL DE EDUCAÇÃO E CULTURA");
          //  paragraph.setAlignment(Element.ALIGN_CENTER);
          //  document.add(paragraph);

            paragraph = new Paragraph("Data :" + currentDateTimeString);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);

            paragraph = new Paragraph(" ");
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);

            //paragraph = new Paragraph("MOVIMENTAÇÃO DE BENS PATRIMONIAIS", FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD));
           // paragraph.setAlignment(Element.ALIGN_CENTER);
           // document.add(paragraph);

          //  paragraph = new Paragraph("Remetente (Local Inicial) :" + fcontent);
          //  paragraph.setAlignment(Element.ALIGN_LEFT);
          //  document.add(paragraph);



            //TODO Tabelas_______________________________________________________________

            PdfPTable table2 = new PdfPTable(6);
            table2.setWidths(new float[]{0.2f,0.7f,1.4f,0.9f,0.4f,0.2f}); //tamanho das colunas
            table2.setTotalWidth(550);//tamanho da tabela
            table2.setLockedWidth(true);



        //    PdfPCell header = new PdfPCell(new Paragraph("Bazar e papelaria Amarelinha de Iguaba", FontFactory.getFont(FontFactory.COURIER, 17, Font.BOLD)));
         //   header.setPadding(7);
          //  header.setHorizontalAlignment(Element.ALIGN_CENTER);
          //  header.setBackgroundColor( BaseColor.LIGHT_GRAY);
          //  header.setColspan(6);//ocupa 3 celulas
          //  table2.addCell(header);


         //   PdfPCell data = new PdfPCell(new Phrase("Data :" + currentDateTimeString));
         //   data.setColspan(6);//ocupa 3 celulas
         //   data.setHorizontalAlignment(Element.ALIGN_LEFT);
          //  table2.addCell(data);


            PdfPCell c2 = new PdfPCell(new Phrase("  "));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setBackgroundColor( BaseColor.LIGHT_GRAY);
            table2.addCell(c2);

            c2 = new PdfPCell(new Phrase("Codigo"));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setBackgroundColor( BaseColor.LIGHT_GRAY);
            table2.addCell(c2);

            c2 = new PdfPCell(new Phrase("Produto"));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setBackgroundColor( BaseColor.LIGHT_GRAY);
            table2.addCell(c2);

            c2 = new PdfPCell(new Phrase("Fornecedor"));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setBackgroundColor( BaseColor.LIGHT_GRAY);
            table2.addCell(c2);

            c2 = new PdfPCell(new Phrase("Preco"));
            c2.setBackgroundColor( BaseColor.LIGHT_GRAY);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(c2);

            c2 = new PdfPCell(new Phrase("qtd"));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setBackgroundColor( BaseColor.LIGHT_GRAY);
            table2.addCell(c2);

            table2.setHeaderRows(1);
            int cont=1;



            //TODO adicionar varios dados na tabela
            for(int i=0;i<dados.size();i++){

                table2.addCell(cont+"");
                cont++;

                table2.addCell(dados.get(i));
                i++;
                table2.addCell(dados.get(i));
                i++;
                table2.addCell(dados.get(i));
                i++;
                table2.addCell(dados.get(i));
                i++;
                table2.addCell(dados.get(i));


            }


            document.add(table2); //adiciona toda a tabela no documento


            // step 5
            document.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }



}