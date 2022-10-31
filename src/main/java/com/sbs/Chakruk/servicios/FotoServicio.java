


package com.sbs.Chakruk.servicios;

import com.sbs.Chakruk.entidades.Foto;
import com.sbs.Chakruk.entidades.Galeria;
import com.sbs.Chakruk.repositorios.FotoRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sbs.Chakruk.errores.ErrorServicio;
import java.io.IOException;
import java.util.List;

@Service
public class FotoServicio {

    
    @Autowired
    private GaleriaServicio galeriaServicio;
    
    
    
    
    @Autowired
    private FotoRepositorio fotoRepositorio; 

    
    
    
   
    @Transactional
    public Foto guardar(MultipartFile archivo) throws ErrorServicio{
    
    if(archivo !=null){
        
        
        try{
      Foto foto = new Foto();
      foto.setMime(archivo.getContentType());
      foto.setNombre(archivo.getName());
      foto.setContenido(archivo.getBytes());
       fotoRepositorio.save(foto);
      
      return foto;
        
        }
        catch(IOException e){
        System.err.println(e.getMessage());
            System.out.println("ERRRRRRRRROOOOOOORR");
       
        }
    }
   return null; 
    }
    
    
    
//    GET-ONE
    public Foto getOne(String id){
        
        
        Foto  foto = fotoRepositorio.getById(id);
        
        
        
        return foto;
        
        
    }
    
    
    //    GET-ALL
    public List<Foto> getAll(){
        
        
       List< Foto>  fotos = fotoRepositorio.findAll();
        
        
        
        return fotos;
        
        
    }
    
    
    
      //    BORRAR-FOTO
    
    
    
    @Transactional
     public void  borrar(String id){
        
         
         
        
        
        fotoRepositorio.deleteById(id);
        
        
        
        
        
    }
    
    
    
    
    
    
}
