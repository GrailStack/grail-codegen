package com.itgrail.grail.codegen.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PloutosResDto {

	private String msg;

	private int ret;

	private DbTablesResDto data;

}
