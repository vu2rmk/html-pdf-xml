package pdf.html.xml.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

//import pdf.html.Documenter;

public class MainClass {
	
	static String SRC = "C:\\Ramkrishna\\projects\\xml-html-parser-poc\\";
	static String DEST = SRC + "merge.pdf";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File file = new File(DEST);
        file.getParentFile().mkdirs();
        String htmlSource = SRC + "Sample.html";
 
        try {
			manipulatePdf(htmlSource, DEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if(true) {
        	
        	return;
        }
		
		String fileName, subject = "", bulletin = "", issueDate = "", footer = "";
		List<String> bodyList = new ArrayList<String>();
		
		//C:\Ramkrishna\projects\xml-html-parser-poc\TestReadFile.xml
		
		Scanner s = new Scanner(System.in);
		
		fileName = s.nextLine();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document document = builder.parse(new File(fileName));
			
			//List<Documenter> docList = new ArrayList<Documenter>();
			
//			Documenter docu;
			//NodeList nodeList = document.getDocumentElement().getChildNodes();
			
			document.getDocumentElement().normalize();
			
			System.out.println("Root element :" + document.getDocumentElement().getNodeName());
			
			NodeList nlist = document.getElementsByTagName("Subject");			
			System.out.println(nlist.getLength());			
			Node nNode = nlist.item(0);			
			System.out.println("\nSubject Element :" + nNode.getNodeName());			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {				
				Element ele = (Element) nNode;				
				System.out.println("\nCurrent Element Content: " + ele.getTextContent());
				subject = ele.getTextContent();
			}
			
			nlist = document.getElementsByTagName("Bulletin");			
			System.out.println(nlist.getLength());			
			nNode = nlist.item(0);			
			System.out.println("Bulletin Element :" + nNode.getNodeName());			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {				
				Element ele = (Element) nNode;				
				System.out.println("\nBulletin Element Content: " + ele.getTextContent());
				bulletin = ele.getTextContent();
			}
			
			nlist = document.getElementsByTagName("LastIssued");			
			System.out.println(nlist.getLength());			
			nNode = nlist.item(0);
			System.out.println("LastIssued Element :" + nNode.getNodeName());			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {				
				Element ele = (Element) nNode;				
				System.out.println("\nLastIssued Element Content: " + ele.getTextContent());
				issueDate = ele.getTextContent();
			}
			
			nlist = document.getElementsByTagName("footer");			
			System.out.println(nlist.getLength());			
			nNode = nlist.item(0);			
			System.out.println("footer Element :" + nNode.getNodeName());			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {				
				Element ele = (Element) nNode;				
				System.out.println("\nfooter Element Content: " + ele.getTextContent());
				footer = ele.getTextContent();
			}
			
			nlist = document.getElementsByTagName("body");
			
			for(int i =0; i< nlist.getLength(); i++) {
				
				Node tnode = nlist.item(i);
				
				System.out.println("Current node element: " + tnode.getNodeName());
				
				if(tnode.getNodeType() == Node.ELEMENT_NODE) {
					
					 Element ele = (Element) tnode;
					 
					 System.out.println("Current node id: " + ele.getAttribute("id"));
					 System.out.println("Current node text: " + ele.getTextContent());
					 bodyList.add(ele.getTextContent());
				}
			}
			/*
			for(int i=0;i<nodeList.getLength(); i++) {
				
				Node node = nodeList.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					
					Element elem = (Element) node;
					
					subject = elem.getElementsByTagName("Subject").item(0).getChildNodes().item(0).getNodeValue();
				}
				
			} */
			
			//System.out.println(nodeList.getLength());\
			
			
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		fileName = "C:\\Ramkrishna\\projects\\xml-html-parser-poc\\MainPage.html";
		String secFileName = "C:\\Ramkrishna\\projects\\xml-html-parser-poc\\ContentPage.html"; 
		
		
		try {
			org.jsoup.nodes.Document doc = Jsoup.parse(new File(fileName), "UTF-8");
			
			org.jsoup.nodes.Element elem = doc.getElementsByTag("th").get(0);
			
			System.out.println("The first th value: " + elem.text());
			
			elem.text("Subject: " + "This is Test subject");
			
			System.out.println("After modification: " + elem.text());
			
			//Lets modify this and print
			doc.getElementsByTag("th").get(0).text("Subject: " + subject);
			doc.getElementsByTag("th").get(1).text("Bulletin: " + bulletin);
			doc.getElementsByTag("th").get(2).text("Last Issued: " + issueDate);
			doc.getElementsByTag("article").get(0).html("<h3>" + bodyList.get(0).toString() + "</h3>");
			
			//System.out.println("The outer html:");
			//System.out.println(doc.outerHtml());
			
			
			HtmlConverter.convertToPdf(doc.outerHtml(), new FileOutputStream("1.pdf"));
			
			doc = Jsoup.parse(new File(secFileName), "UTF-8");
			doc.getElementsByTag("th").get(0).text("Subject: " + subject);
			doc.getElementsByTag("th").get(1).text("Bulletin: " + bulletin);
			doc.getElementsByTag("article").get(0).html("<h3>" + bodyList.get(1).toString() + "</h3>");			
			
			
			HtmlConverter.convertToPdf(doc.outerHtml(), new FileOutputStream("2.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File mergeFile = new File("merge.pdf");
		
		try {
			PdfReader[] readArray = {
					new PdfReader("1.pdf"),
					new PdfReader("2.pdf")					
			};
			
			mergeForms("merge.pdf", readArray);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
	}
	
	public static void mergeForms(String dest, PdfReader[] readers) throws FileNotFoundException {
		
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
		
		pdfDoc.initializeOutlines();
		
		PdfPageFormCopier copier = new PdfPageFormCopier();
		
		for(PdfReader reader: readers) {
			
			PdfDocument readerDoc = new PdfDocument(reader);
			readerDoc.copyPagesTo(1,readerDoc.getNumberOfPages(), pdfDoc, copier);
			
			readerDoc.close();
		}
		
		pdfDoc.close();
		
	}
	
	 public static void manipulatePdf(String htmlSource, String pdfDest) throws IOException {
	        PdfWriter writer = new PdfWriter(pdfDest);
	        PdfDocument pdfDocument = new PdfDocument(writer);
	        
	        String fileName = "C:\\Ramkrishna\\projects\\xml-html-parser-poc\\iText_Sample_Template\\SI_template_fr_CA_Header.html";
	        String header = "pdfHtml Header and footer example using page-events";
	        
	        org.jsoup.nodes.Document doc = Jsoup.parse(new File(fileName), "UTF-8");
	        header = doc.outerHtml();
	        
	        
	        Header headerHandler = new Header(header);
	        Footer footerHandler = new Footer();
	 
	        pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, headerHandler);
	        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);
	 
	        // Base URI is required to resolve the path to source files
	        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SRC);
	        HtmlConverter.convertToDocument(new FileInputStream(htmlSource), pdfDocument, converterProperties);
	        
	        
	 
	        // Write the total number of pages to the placeholder
	        footerHandler.writeTotal(pdfDocument);
	        pdfDocument.close();
	    }

}

// Header event handler
class Header implements IEventHandler {
    private String header;

    public Header(String header) {
        this.header = header;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdf = docEvent.getDocument();
        
        

        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();
        
        
        try {
			List<IElement> headerElements = HtmlConverter.convertToElements(header);
			
			Canvas canvas = new Canvas(new PdfCanvas(page), pdf, pageSize);
			//Canvas canvas = new Canvas(canvas, pdfDoc, new Rectangle(36, 20, page.getPageSize().getWidth() - 72, 50));
	        canvas.setFontSize(10);
	        
	
			
			for(IElement headerElement: headerElements) {
				
				if(headerElement instanceof IBlockElement) {
					
					canvas.add((IBlockElement)headerElement);
				}
			}
	

//	        
//	        // Write text at position
//	        canvas.showTextAligned(header,
//	                pageSize.getWidth() / 2,
//	                pageSize.getTop() - 30, TextAlignment.CENTER);
	        canvas.close();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

// Footer event handler
class Footer implements IEventHandler {
    protected PdfFormXObject placeholder;
    protected float side = 20;
    protected float x = 300;
    protected float y = 25;
    protected float space = 4.5f;
    protected float descent = 3;

    public Footer() {
        placeholder = new PdfFormXObject(new Rectangle(0, 0, side, side));
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdf = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        int pageNumber = pdf.getPageNumber(page);
        Rectangle pageSize = page.getPageSize();
        
        String html = "C:\\Ramkrishna\\projects\\xml-html-parser-poc\\iText_Sample_Template\\SI_template_fr_CA_Footer.html";
        
        List<IElement> footerElements = null;
        
        try {
			footerElements = HtmlConverter.convertToElements(html);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Creates drawing canvas
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
        
        for (IElement footerElement : footerElements) {
            // Making sure we are adding blocks to canvas
            if (footerElement instanceof IBlockElement) {
                canvas.add((IBlockElement)footerElement);
            }
        }

//        Paragraph p = new Paragraph()
//                .add("Page ")
//                .add(String.valueOf(pageNumber))
//                .add(" of");
//
//        canvas.showTextAligned(p, x, y, TextAlignment.RIGHT);
        canvas.close();

        // Create placeholder object to write number of pages
//        pdfCanvas.addXObject(placeholder, x + space, y - descent);
//        pdfCanvas.release();
    }

    public void writeTotal(PdfDocument pdf) {
        Canvas canvas = new Canvas(placeholder, pdf);
        canvas.showTextAligned(String.valueOf(pdf.getNumberOfPages()),
                0, descent, TextAlignment.LEFT);
        canvas.close();
    }
}

