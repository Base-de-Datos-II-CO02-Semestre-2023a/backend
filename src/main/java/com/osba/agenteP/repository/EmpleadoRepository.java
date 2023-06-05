package com.osba.agenteP.repository;

import com.osba.agenteP.domain.Empleado;
import com.osba.agenteP.domain.RegistroContratos;
import com.osba.agenteP.model.EmpleadoEncontrado;
import com.osba.agenteP.model.EmpleadoProductivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    public Optional<Empleado> findByRfc(String rfc);
    public Optional<Empleado> findEmpleadoById(Integer id);
    @Query(value = "select * from empleado where contrato is not null order by nombre", nativeQuery = true)
    public List<Empleado> findEmpleadosActivos();

    @Query(value = "select id, rfc, nombre, indice_productividad as indiceProductividad from empleado where contrato is not null order by indice_productividad desc limit 14", nativeQuery = true)
    public List<EmpleadoProductivo> obtenerMásProductivos();

    @Query(value = "select id, rfc, nombre, indice_productividad as indiceProductividad from empleado where contrato is not null  order by indice_productividad asc limit 14", nativeQuery = true)
    public List<EmpleadoProductivo> obtenerMenosProductivos();

    @Query(value = "select id from empleado where rfc = :rfc", nativeQuery = true)
    public Integer findIdByRfc(String rfc);

    @Query(value = "select e.id as id," +
            " e.rfc as rfc," +
            " e.nombre as nombre," +
            " rc.puesto as puesto," +
            " rc.salario as salario," +
            " e.indice_productividad as indiceProductividad," +
            " rc.id_lugar as idLugar," +
            " e.fecha_de_ingreso as fechaDeIngreso " +
            "from empleado e inner join registro_contratos rc on e.contrato = rc.id"+
            " where " +
            "(lower(rfc) like('%'||lower(:query)||'%') or " +
            "lower(nombre) like('%'||lower(:query)||'%') or " +
            "lower(correo) like('%'||lower(:query)||'%'))",nativeQuery = true)
    public List<EmpleadoEncontrado> findEmpleadosByRfcOrNombreOrCorreo(String query);
}