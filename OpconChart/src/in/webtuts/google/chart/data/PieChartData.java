package in.webtuts.google.chart.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.webtuts.google.chart.spring.daos.OpconDAODB;
import in.webtuts.google.opcon.Estado;
import in.webtuts.google.opcon.Opcon;

public class PieChartData {

	private static List<KeyValue> data;

	public static List<KeyValue> getPieDataList() {
		data = new ArrayList<PieChartData.KeyValue>();
		Calendar calendar = Calendar.getInstance();
		java.sql.Date to = new java.sql.Date(calendar.getTime().getTime());
		calendar.setTime(to);
		calendar.add(Calendar.DATE, -30);
		java.sql.Date from = new java.sql.Date(calendar.getTime().getTime());

		List<Estado> estados = new ArrayList<Estado>();
		estados = getTimeFromStatus(from, to);

		int i = 0;

		while (i < estados.size()) {
			data.add(new KeyValue(estados.get(i).getNombre(), Integer.toString(estados.get(i).getTiempo())));
			i++;
		}

		return data;
	}

	public static class KeyValue {
		String key;
		String value;

		public KeyValue(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	public static List<Estado> getTimeFromStatus(Date from, Date to) {
		List<Estado> estados = new ArrayList<Estado>();
		OpconDAODB db = new OpconDAODB();

		System.out.println("Buscando opcons desde " + from + " hasta " + to);

		List<Opcon> opcons = db.getAllOpcons(from, to);

		int i = 0;
		int x = 0;
		String nombreEstado = "";
		int tiempoEstado = 0;

		while (i < opcons.size()) {
			System.out.println("----- ISSUENUM = " + opcons.get(i).getIssueNum());
			x = 0;
			while (x < opcons.get(i).getEstado().size()) {

				nombreEstado = opcons.get(i).getEstado().get(x).getNombre();
				tiempoEstado = opcons.get(i).getEstado().get(x).getTiempo();

				// System.out.println(opcons.get(i).getEstado().get(x).getNombre()
				// + " = " + opcons.get(i).getEstado().get(x).getTiempo());

				if (!checkIfEstadoExist(nombreEstado, estados)) {
					Estado e = new Estado();
					e.setNombre(nombreEstado);
					e.setTiempo(tiempoEstado);
					estados.add(e);
				} else {
					estados.get(getIndexFromEstado(nombreEstado, estados)).setTiempo(tiempoEstado);
				}

				x++;
			}
			i++;
		}

		return estados;
	}

	private static boolean checkIfEstadoExist(String estado, List<Estado> estados) {
		int i = 0;
		while (i < estados.size()) {

			if (estados.get(i).getNombre().equals(estado)) {
				// System.out.println("EQUAL");
				return true;
			}
			i++;
		}
		return false;
	}

	private static int getIndexFromEstado(String estado, List<Estado> estados) {
		int i = 0;
		while (i < estados.size()) {
			// System.out.println("Estado = " + this.estado.get(i).getNombre());
			if (estados.get(i).getNombre().equals(estado)) {
				return i;
			}
			i++;
		}
		return -1;
	}
}