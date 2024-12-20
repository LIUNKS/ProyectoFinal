package Controlador;

import Modelo.Conexion;
import Modelo.Config;
import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class ProductoDAO implements _Operaciones{
    
    //DATOS PARA INTERACTUAR CON LA BASE DE DATOS
    Connection connection;
    PreparedStatement declaracion;
    ResultSet resultado;

    //INSTANCIAS
    Conexion conexion = new Conexion();
    
    //METODO PARA REGISTRAR LOS PRODUCTOS EN LA BASE DE DATOS
    public boolean registrarProducto(Producto producto) {
        String sql = "INSERT INTO productos (codigo, nombre, proveedor, stock, precio) VALUES (?, ?, ?, ?, ?)";
        try {
            
            connection = conexion.getConnection();
            declaracion = connection.prepareStatement(sql);
            declaracion.setString(1, producto.getCodigo());
            declaracion.setString(2, producto.getNombre());
            declaracion.setString(3, producto.getProveedor());
            declaracion.setInt(4, producto.getStock());
            declaracion.setDouble(5, producto.getPrecio());
            declaracion.execute();
            return true;

        } catch (SQLException e) {
            
            System.out.println(e.toString());
            return false;
            
        } finally {
            
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            
        }
    }
    
    //METODO PARA CONSULTAR PROVEEDOR EN LA SECCION DE PRODUCTOS
    public void consultarProveedor(JComboBox proveedor ){
        String sql = "SELECT nombre FROM proveedor";
        
        try {
            
            connection = conexion.getConnection();
            declaracion = connection.prepareStatement(sql);
            resultado = declaracion.executeQuery();
            while (resultado.next()) {                
                proveedor.addItem(resultado.getString("nombre"));
            }
            
        } catch (SQLException e) {
            
            System.out.println(e.toString());
            
        }
    }
    
    //METODO PARA ACTUALIZAR DATOS DEL PRODUCTO
    public boolean modificarProducto(Producto producto){
        
        String sql = "UPDATE productos SET codigo=?, nombre=?, proveedor=?, stock=?, precio=? WHERE id=?";
        try {
            declaracion = connection.prepareStatement(sql);
            declaracion.setString(1, producto.getCodigo());
            declaracion.setString(2, producto.getNombre());
            declaracion.setString(3, producto.getProveedor());
            declaracion.setInt(4, producto.getStock());
            declaracion.setDouble(5, producto.getPrecio());
            declaracion.setInt(6, producto.getId());
            declaracion.execute();
            
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            
            return false;
            
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    //ASIGNA LA INFORMACION DE LA BASE DE DATOS A LA TABLA DE PRODUCTOS
    @Override
    public List listar(){
        List<Producto> listaProducto = new ArrayList();
        String sql = "SELECT * FROM productos";
        
        try {
            connection = conexion.getConnection();
            declaracion = connection.prepareStatement(sql);
            resultado = declaracion.executeQuery();
            while (resultado.next()) {
                Producto producto = new Producto();
                producto.setId(resultado.getInt("id"));
                producto.setCodigo(resultado.getString("codigo"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setProveedor(resultado.getString("proveedor"));
                producto.setStock(resultado.getInt("stock"));
                producto.setPrecio(resultado.getDouble("precio"));
                listaProducto.add(producto);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        
        return listaProducto;
    }
    
    //METODO PARA ELIMINAR PRODUCTOS
    @Override
    public boolean eliminar(int id){
        //CONSULTA A LA BASE DE DATOS
        String sql = "DELETE FROM productos WHERE id = ?";
        //MANEJO DE EXCEPCIONES
        try {
            declaracion = connection.prepareStatement(sql);
            declaracion.setInt(1, id);
            declaracion.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    //METODO PARA BUSCAR EL PRODUCTO
    public Producto buscarProducto(String codigo){
        
        Producto producto = new Producto();
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        
        try {
            
            connection = conexion.getConnection();
            declaracion = connection.prepareStatement(sql);
            declaracion.setString(1, codigo);
            resultado = declaracion.executeQuery();
            
            if(resultado.next()){
                producto.setNombre(resultado.getString("nombre"));
                producto.setPrecio(resultado.getDouble("precio"));
                producto.setStock(resultado.getInt("stock"));
            }
            
        } catch (SQLException e) {
            
            System.out.println(e.toString());
            
        }
        
        return producto;
    }
    
    //METODO PARA BUSCAR EL PRODUCTO
    public Config buscarDatos(){
        
        Config config = new Config();
        String sql = "SELECT * FROM config";
        
        try {
            
            connection = conexion.getConnection();
            declaracion = connection.prepareStatement(sql);
            resultado = declaracion.executeQuery();
            
            if(resultado.next()){
                config.setId(resultado.getInt("id"));
                config.setRuc(resultado.getInt("ruc"));
                config.setNombre(resultado.getString("nombre"));
                config.setTelefono(resultado.getInt("telefono"));
                config.setDireccion(resultado.getString("direccion"));
                config.setRazon(resultado.getString("razon"));
            }
            
        } catch (SQLException e) {
            
            System.out.println(e.toString());
            
        }
        
        return config;
    }
    
    //METODO PARA ACTUALIZAR DATOS DE LA EMPRESA
    public boolean modificarDatos(Config config){
        
        String sql = "UPDATE config SET ruc=?, nombre=?, telefono=?, direccion=?, razon=? WHERE id=?";
        try {
            declaracion = connection.prepareStatement(sql);
            declaracion.setInt(1, config.getRuc());
            declaracion.setString(2, config.getNombre());
            declaracion.setInt(3, config.getTelefono());
            declaracion.setString(4, config.getDireccion());
            declaracion.setString(5, config.getRazon());
            declaracion.setInt(6, config.getId());
            declaracion.execute();
            
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            
            return false;
            
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
}
