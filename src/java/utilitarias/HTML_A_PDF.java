/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

//import com.itextpdf.text.BadElementException;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.pdf.codec.Base64;
//import com.itextpdf.tool.xml.XMLWorker;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
//import com.itextpdf.tool.xml.html.Tags;
//import com.itextpdf.tool.xml.parser.XMLParser;
//import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
//import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
//import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
//import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
//import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
//import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;


/**
 *
 * @author manuel.alcala
 */
public class HTML_A_PDF {
//    public  void crearPdf(String pathFile,String htmltoPdf) throws IOException, DocumentException {
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathFile));
//        document.open();
//        CSSResolver cssResolver =
//        XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
//        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
//        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
//        htmlContext.setImageProvider(new Base64ImageProvider());
//        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
//        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
//        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
//        XMLWorker worker = new XMLWorker(css, true);
//        XMLParser p = new XMLParser(worker);
//        p.parse(new ByteArrayInputStream(htmltoPdf.getBytes()));
//        document.close();
//    }
//     class Base64ImageProvider extends AbstractImageProvider {
// 
//        @Override
//        public Image retrieve(String src) {
//            int pos = src.indexOf("base64,");
//            try {
//                if (src.startsWith("data") && pos > 0) {
//                    byte[] img = Base64.decode(src.substring(pos + 7));
//                    return Image.getInstance(img);
//                }
//                else {
//                    return Image.getInstance(src);
//                }
//            } catch (BadElementException ex) {
//                return null;
//            } catch (IOException ex) {
//                return null;
//            }
//        }
//        @Override
//        public String getImageRootPath() {
//            return null;
//        }
//    }
}
