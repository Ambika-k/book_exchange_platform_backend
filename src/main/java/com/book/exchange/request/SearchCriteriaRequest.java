package com.book.exchange.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteriaRequest {
	private String title;
	private String author;
	private String genre;
	private String location;
	private List<String> availabilityStatuses;
}
