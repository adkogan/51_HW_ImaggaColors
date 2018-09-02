package telran.imagga.controller;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import telran.imagga.dto.Details1;
import telran.imagga.dto.Details2;
import telran.imagga.dto.InfoDTO;
import telran.imagga.dto.ResponseDto;

public class ImmagaColorAppl {

	public static void main(String[] args) {
		
		RestTemplate  restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", 
				"secret code");
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		String url = "https://api.imagga.com/v1/colors";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("url", "https://imagga.com/static/images/tagging/wind-farm-538576_640.jpg");
				
		ResponseEntity<ResponseDto> response = restTemplate.exchange(
				builder.build().encode().toUri(),
				HttpMethod.GET, 
				requestEntity,
				ResponseDto.class);
		
		display(response.getBody().getResults()[0].getInfo());

	}

	private static void display(InfoDTO infoDTO) {
		printBackgroundColors(infoDTO);
		
		printForegroundColors(infoDTO);
		
		printImageColors(infoDTO);
	}

	private static void printImageColors(InfoDTO infoDTO) {
		System.out.println("Image colors");
		System.out.format("%32s%32s%32s\n", "color name", "parent color name" , "coverage percent");
		displayGroup(infoDTO.getImage_colors());
	}

	private static void printForegroundColors(InfoDTO infoDTO) {
		System.out.println("Foreground colors");
		System.out.format("%32s%32s%32s\n", "color name", "parent color name" , "coverage percent");
		displayGroup(infoDTO.getForeground_colors());
	}

	private static void printBackgroundColors(InfoDTO infoDTO) {
		System.out.println("Backgroup colors");
		System.out.format("%32s%32s%32s\n", "color name", "parent color name" , "coverage percent");
		displayGroup(infoDTO.getBackground_colors());
	}

	private static void displayGroup(Details2[] image_colors) {
		Arrays.stream(image_colors)
		.forEach(c -> System.out.format("%32s%32s%32s\n", 
				c.getClosest_palette_color(), 
				c.getClosest_palette_color_parent(),
				c.getPercent()));
		
	}

	private static void displayGroup(Details1[] background_colors) {
		Arrays.stream(background_colors)
		.forEach(c -> System.out.format("%32s%32s%32s\n",
				c.getClosest_palette_color(),
				c.getClosest_palette_color_parent(),
				c.getPercentage()));
		
	}

}
