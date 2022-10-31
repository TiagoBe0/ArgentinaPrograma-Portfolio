


package com.sbs.Chakruk.controladores;

import com.sbs.Chakruk.errores.ErrorServicio;
import com.sbs.Chakruk.servicios.ComboServicio;
import com.sbs.Chakruk.servicios.FotoServicio;
import com.sbs.Chakruk.servicios.GaleriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

@Controller
public class ComboControlador {

    
    
    @Autowired
    private ComboServicio comboServicio;
        @Autowired
    private GaleriaServicio galeriaServicio;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    
            //   REGISTRAR
      @PostMapping("/registroCombo")
    public String registrar(ModelMap modelo , @RequestParam String nombre,@RequestParam Float precio,@RequestParam Float descuento,@RequestParam int cantidad,MultipartFile archivo) throws ErrorServicio, Exception{
      
       
      
       try{
         if(archivo!=null){
    comboServicio.nuevo(nombre, precio, descuento, cantidad, archivo);
         }
      return "index.html";
       }catch(ErrorServicio ex){
           
          
         
              
            
           
           return "registroUsuario.html";
       }
    }
    
    //MODIFICAR COMBO
    @PostMapping("/modificarCombo")
   public String modificar(ModelMap modelo,@RequestParam String id ,String nombre ,Float precio ,Float descuento , int cantidad,MultipartFile archivo, Boolean alta) throws Exception{
   
   
       try{
         if(archivo!=null){
    comboServicio.modificar(archivo, id, precio, nombre, descuento, alta, cantidad);
         }
      return "index.html";
       }catch(ErrorServicio ex){
           
   
   return "index.html";
   }
   }
    
}
