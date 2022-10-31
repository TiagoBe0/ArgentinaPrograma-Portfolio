


package com.sbs.Chakruk.controladores;

import com.sbs.Chakruk.entidades.Foto;
import com.sbs.Chakruk.entidades.Galeria;
import com.sbs.Chakruk.errores.ErrorServicio;
import com.sbs.Chakruk.servicios.FotoServicio;
import com.sbs.Chakruk.servicios.GaleriaServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/foto")
public class FotoControlador {
    
    @Autowired
    private GaleriaServicio galeriaServicio;
    
     @Autowired
    private FotoServicio fotoServicio;
    
//    FOTO
   @GetMapping("/galeria")
    public ResponseEntity<byte[]> fotoAlbum(@RequestParam String idGaleria){
    
        
        try {
            Galeria galeria = galeriaServicio.getOne(idGaleria);
            
            if(galeria.getFotos() == null){
                
                System.out.println("La galeria esta vacia.");
            }
        byte[] foto = galeria.getFotos().get(2).getContenido();
        
     if(foto == null){
                
                System.out.println("La foto no existe");
            }
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
            
        return new ResponseEntity<> (foto,headers,HttpStatus.OK);
            
       } catch (Exception ex ) {
           
           Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE ,null , ex);
           return new ResponseEntity<> (HttpStatus.NOT_FOUND);
       }
       
        
        

    }    

    
    
    //MOSTRAR FOTOS
      @GetMapping("/fotografia")
    public ResponseEntity<byte[]> foto(@RequestParam String idFoto){
    
        
        try {
            Foto fotoG = fotoServicio.getOne(idFoto);
            
            if(fotoG ==null){
                
                System.out.println("La galeria esta vacia.");
            }
        byte[] foto = fotoG.getContenido();
        
     if(foto == null){
                
                System.out.println("La foto no existe");
            }
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
            
        return new ResponseEntity<> (foto,headers,HttpStatus.OK);
            
       } catch (Exception ex ) {
           
           Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE ,null , ex);
           return new ResponseEntity<> (HttpStatus.NOT_FOUND);
       }
    
    
    }
    
}
