package com.osba.agenteP.repository;

import com.osba.agenteP.domain.RegistroContratos;
import jakarta.persistence.StoredProcedureParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import javax.xml.crypto.dsig.XMLObject;
import java.util.Optional;

public interface RegistroContratosRepository extends JpaRepository<RegistroContratos, Integer> {
    Optional<RegistroContratos> findFirstByIdEmpleado(int idEmpleado);
    Optional<RegistroContratos>  findById(int id);
    @Query(value = "select * from cast ((select * from query_to_xml( 'select * from modificacion_contrato where id_contrato = (select contrato from empleado where rfc = '||:rfc||')',true,false,'')) as varchar)", nativeQuery = true)
    Object getReporteModificaciones( @Param(value = "rfc") String ejemplo);

    @Query(value = "SELECT COUNT(*) FROM registro_contratos WHERE ((fecha_fin - current_date) < 14 ) AND (fecha_fin > current_date)", nativeQuery = true)
    public Integer getConcluirContratos();

    @Query(value = "SELECT COUNT(*) FROM registro_contratos WHERE ((fecha_fin - current_date) < 14 ) AND (fecha_fin > current_date) AND (id_lugar = :id_lugar)", nativeQuery = true)
    public Integer getConcluirContratosLugar(Integer id_lugar);

    @Query(value = "SELECT dias_vacaciones FROM registro_contratos WHERE id_empleado = :id_empleado", nativeQuery = true)
    public Integer getVacacionesbyId(Integer id_empleado);



}

