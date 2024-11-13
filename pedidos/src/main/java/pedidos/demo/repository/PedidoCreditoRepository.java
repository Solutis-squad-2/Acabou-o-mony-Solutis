package pedidos.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pedidos.demo.model.PedidoCredito;

public interface PedidoCreditoRepository extends JpaRepository<PedidoCredito,Long> {
}
