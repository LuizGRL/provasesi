package br.senai.sc.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Funcoes {
	
	public LocalDate converterData(Date data) {
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormatada = sdf.format(data);
        String[] partesData = dataFormatada.split("-");
        LocalDate dataConvertida = LocalDate.of(Integer.parseInt(partesData[0]),Integer.parseInt(partesData[1]),Integer.parseInt(partesData[2]));
		return dataConvertida;
	}	

}
