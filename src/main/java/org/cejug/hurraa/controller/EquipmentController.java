/*
* Hurraa is a web application conceived to manage resources
* in companies that need manage IT resources. Create issues
* and purchase IT materials. Copyright (C) 2014 CEJUG.
*
* Hurraa is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Hurraa is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Hurraa. If not, see http://www.gnu.org/licenses/gpl-3.0.html.
*
*/
package org.cejug.hurraa.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.cejug.hurraa.model.Equipment;
import org.cejug.hurraa.model.bean.EquipmentBean;
import org.cejug.hurraa.model.bean.EquipmentModelBean;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Path("equipment")
@Controller
public class EquipmentController {
	
	
	private Result result;
	private EquipmentBean equipmentBean;
	private EquipmentModelBean equipmentModelBean;

	@Deprecated
	public EquipmentController() {};

	@Inject
	public EquipmentController(Result result, EquipmentBean equipmentBean, EquipmentModelBean equipmentModelBean ) {
        this.result = result;
        this.equipmentBean = equipmentBean;
        this.equipmentModelBean = equipmentModelBean;
	}
	
    @Path(value = { "", "/" })
    public void index() {
        result.forwardTo(EquipmentController.class).list();
    }
    
    @Path("form")
    public void form() {
        result.include("models" , equipmentModelBean.findAll() );
    }
    
    @Path("form/{id}")
    public void form(Long id) {
        Equipment equipment = equipmentBean.findById(id);
        result.include( equipment );
        result.include("models" , equipmentModelBean.findAll() );
        
    }
    
    @Post("insert")
    public void insert(@Valid  Equipment equipment, Validator validator ) {
    	validator.onErrorForwardTo( EquipmentController.class ).form();
    	equipmentBean.insert(equipment);
        result.redirectTo(EquipmentController.class).list();
    }
    
    @Get("list")
    public void list() {
        result.include("equipments", equipmentBean.findAll());
    }
    
    @Post
    @Path("update")
    public void update(@Valid Equipment equipment, Validator validator) {
    	validator.onErrorForwardTo( SectorController.class ).form();
    	equipmentBean.update(equipment);
    	result.redirectTo(EquipmentController.class).list();
    }

    @Path("delete/{equipment.id}")
    public void delete(Equipment equipment) {
    	equipmentBean.delete(equipment);
        result.redirectTo(EquipmentController.class).list();
    }
    


}
