package com.mawi.sistemagestionventas.services;

import com.mawi.sistemagestionventas.dto.DetalleFacturaDTO;
import com.mawi.sistemagestionventas.dto.FacturaDTO;
import com.mawi.sistemagestionventas.interfaces.CRUDInterface;
import com.mawi.sistemagestionventas.models.Factura;
import com.mawi.sistemagestionventas.models.Pedido;
import com.mawi.sistemagestionventas.repository.FacturaRepository;
import com.mawi.sistemagestionventas.repository.PedidoRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService implements CRUDInterface<Factura, Integer> {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura findById(Integer id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
    }

    @Override
    @Transactional
    public Factura save(Factura nuevaFactura) {
        if (nuevaFactura.getPedido() == null || nuevaFactura.getPedido().getIdPedido() == 0) {
            throw new IllegalArgumentException("La factura debe estar asociada a un pedido");
        }

        Pedido pedido = pedidoRepository.findById(nuevaFactura.getPedido().getIdPedido())
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        nuevaFactura.setPedido(pedido);

        return facturaRepository.save(nuevaFactura);
    }

    @Override
    public Factura update(Integer id, Factura entity) {
        throw new UnsupportedOperationException("No se permite modificar una factura");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("No se permite eliminar una factura");
    }

    public FacturaDTO obtenerFacturaConDetalles(Integer idFactura) {
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        FacturaDTO dto = new FacturaDTO();
        dto.setIdFactura(factura.getIdFactura());
        dto.setFechaEmision(factura.getPedido().getFechaPedido().toString());
        dto.setClienteNombre(factura.getPedido().getCliente().getNombre() + " " + factura.getPedido().getCliente().getApellido());
        dto.setEmpleadoNombre(factura.getPedido().getEmpleado().getNombre() + " " + factura.getPedido().getEmpleado().getApellido());

        List<DetalleFacturaDTO> detallesDTO = factura.getDetalles().stream().map(detalle -> {
            DetalleFacturaDTO d = new DetalleFacturaDTO();
            d.setProductoNombre(detalle.getProducto().getNombre());
            d.setCantidad(detalle.getCantidad());
            d.setPrecioUnitario(detalle.getPrecioUnitario());
            d.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
            return d;
        }).collect(Collectors.toList());

        dto.setDetalles(detallesDTO);

        double total = detallesDTO.stream()
                .mapToDouble(DetalleFacturaDTO::getSubtotal)
                .sum();
        dto.setTotal(total);

        return dto;
    }
}
