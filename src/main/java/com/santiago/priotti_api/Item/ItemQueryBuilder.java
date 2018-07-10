package com.santiago.priotti_api.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemQueryBuilder {

    public String build(List<String> keywords){
        String query = "SELECT codigo, aplicacion, marca, rubro, info, precio_lista, precio_oferta, imagen FROM productos WHERE ";
        String out = keywords.stream()
                .map(it -> "(codigo LIKE \"%"+it+"%\" OR  aplicacion Like \"%"+it+"%\" OR" +
                        " marca LIKE \"%"+it+"%\" OR  rubro LIKE \"%"+it+"%\" OR" +
                        "  info LIKE \"%"+it+"%\") AND ")
                .collect(Collectors.joining(""));
        query+=out;
        query+="vigente = 1";
        return query;
    }
}


    /*private static function obtenerLista($tipo){
        $busqueda = $_POST["busqueda"];

        $cont = 0;

        switch($tipo){
            case 2:
                $limite = 'limit 100';
                $tipo = 'vigente = 1';
                break;
            case 3:
                $limite = '';
                $tipo = 'precio_oferta > 0';
                break;
            case 4:
                $limite = '';
                $tipo = 'fecha_agregado > date_sub( now(), interval 2 month) order by fecha_agregado desc';
                break;
        }

        $p = explode(" ", $busqueda);

        $query = 'select codigo, aplicacion, marca, rubro, info, precio_lista, precio_oferta, imagen FROM productos WHERE ';

        foreach($p as $clave){
            if($cont == 5) break;
            $query .= '(codigo LIKE "%'.$clave.'%" OR  aplicacion Like "%'.$clave.'%" OR
            marca LIKE "%'.$clave.'%" OR  rubro LIKE "%'.$clave.'%" OR  info LIKE "%'.$clave.'%") and ';
            $cont++;
        }

        $query .= $tipo.' '.$limite;

        $conexion = Conexion::conectar();
        try{
            $resultset = $conexion->query($query);
            if($resultset->num_rows!=0){
                while($row = $resultset->fetch_assoc()) {
                    $row["info"] = utf8_encode($row["info"]);
                    $tabla["data"][] = $row;
                }
            }else{
                return '{ "data": [] }';
            }

        }catch(Exception $e){
            return $e;
        };

        $resultset->free();

        $conexion->close();

        return json_encode($tabla);
    }*/
