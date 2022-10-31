


package com.sbs.Chakruk.servicios;

import com.sbs.Chakruk.entidades.Combo;
import com.sbs.Chakruk.entidades.Foto;
import com.sbs.Chakruk.entidades.Usuario;
import com.sbs.Chakruk.errores.ErrorServicio;
import com.sbs.Chakruk.repositorios.ComboRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ComboServicio {

    
        @Autowired
    private FotoServicio fotoServicio;
        
        @Autowired
    private ComboRepositorio comboRepositorio;
        
        
    
    
    //CARGAR NUEVO COMBO
      @Transactional
    public void nuevo( String nombre, Float precio, Float descuento,int cantidad, MultipartFile archivo ) throws ErrorServicio,Exception{
    
        
  
      
      Combo combo = new Combo();

      combo.setCantidad(cantidad);
      combo.setDescripcion(nombre);
      combo.setDescuento(descuento);
      combo.setEstado(true);  
      combo.setPrecio(precio);
  
        Foto foto = fotoServicio.guardar(archivo);
        combo.setFoto(foto);
        
        comboRepositorio.save(combo);
    

    
    }

    
    //MODIFICAR COMBO
    @Transactional
    public void modificar(MultipartFile archivo ,String id,Float precio, String nombre,Float descuento,Boolean baja,int cantidad) throws ErrorServicio,Exception{
        
        
        Optional<Combo> respuesta = comboRepositorio.findById(id);
        if(respuesta.isPresent()){
        Combo combo=respuesta.get();
        combo.setCantidad(cantidad);
        combo.setDescripcion(nombre);
        combo.setPrecio(precio);
        combo.setDescuento(descuento);
        if(baja){
        
            combo.setEstado(true);
        }
        
             
      if(archivo != null){
      
          Foto foto = fotoServicio.guardar(archivo);
      
      
      }
        }
      
   
        
        
        



    }
    
    
    //DAR DE ALTA
    @Transactional
    public void darAlta(String id){
    Optional<Combo> respuesta = comboRepositorio.findById(id);
        if(respuesta.isPresent()){
        Combo  combo = respuesta.get();
        
        
        combo.setEstado(true);
        
        
        }
    
    }
    
    
       //DAR DE ALTA
    @Transactional
    public void darBaja(String id){
    Optional<Combo> respuesta = comboRepositorio.findById(id);
        if(respuesta.isPresent()){
        Combo  combo = respuesta.get();
        
        
        combo.setEstado(false);
        
        
        }
    
    }
    
}
