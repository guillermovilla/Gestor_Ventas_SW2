import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;


public class GeneratePDFFile {
	
	private DBConection oDBConection;
	
	private Connection cn;
	
	public GeneratePDFFile() {
		
	}
	
	public void creaPDF(Date date) {
		
		oDBConection = new DBConection();
		cn = oDBConection.connect();
		
		Document document = new Document();
		DateFormat fechahora = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		DateFormat fechaimprimir = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		DateFormat fechaFormat = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
		
		String nombre = fechahora.format(date);
		nombre += ".pdf";

		try {
			String sentenciaSQL = "SELECT * FROM compra";
			String sentenciaSQL2 = "SELECT idventa FROM log WHERE fecha = '" + fechaFormat.format(date) + "' and hora = '" + horaFormat.format(date) + "'";
			String producto = "";
			int precio = 0;
			String precioS = "";
			int total = 0;
			String totalS = "";
			int v = LoginWindow.id;
			int idVenta = 0;
			String idS = null;
			
			Statement st2 = cn.createStatement();
			ResultSet rs2 = st2.executeQuery(sentenciaSQL2);
			
			while(rs2.next()) {
				idVenta = rs2.getInt("idventa");
				idS = Integer.toString(idVenta);
			}
			
			PdfWriter.getInstance(document, new FileOutputStream(nombre));
			document.open();

			document.add(new Paragraph("Factura nº  " + idS + "\n"));

			document.add(new Paragraph("Vendedor:  " + v));

			
			String fecha = fechaimprimir.format(date);
			document.add(new Paragraph("Fecha: "+ fecha));
			document.add(new Paragraph(" "));

			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sentenciaSQL);
			
			PdfPTable table = new PdfPTable(2);
			for(int aw=0;aw<1 ; aw++){
				table.addCell("Producto");
				table.addCell("Precio");
			}
			
			while(rs.next()) {
				producto = rs.getString("nombre");
				precio = rs.getInt("precio");
				total += precio;
				precioS = Integer.toString(precio);
				totalS = Integer.toString(precio);
				
				for(int aw=0;aw<1 ; aw++){
					table.addCell(producto);
					table.addCell(precio + "€");
				}
			}
			for(int aw=0;aw<1 ; aw++){
				table.addCell("Precio total");
				table.addCell(totalS + "€");
			}

			document.add(table);
			//document.add(new Paragraph("Precio total: " + totalS + "€"));
			// step 6
			document.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
