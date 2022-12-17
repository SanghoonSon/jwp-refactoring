package kitchenpos.table.application;

import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.table.dto.OrderTableChangeRequest;
import kitchenpos.table.dto.OrderTableCreateRequest;
import kitchenpos.table.dto.OrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TableService {
    private final OrderTableRepository orderTableRepository;

    public TableService(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderTableResponse createOrderTable(final OrderTableCreateRequest orderTable) {
        return OrderTableResponse.of(orderTableRepository.save(orderTable.toOrderTable()));
    }

    public List<OrderTableResponse> findAllOrderTables() {
        return OrderTableResponse.list(orderTableRepository.findAll());
    }

    @Transactional
    public OrderTableResponse changeEmpty(final Long orderTableId, final OrderTableChangeRequest request) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(EntityNotFoundException::new);
        orderTable.changeEmpty(request.isEmpty());
        return OrderTableResponse.of(orderTable);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final Long orderTableId, final OrderTableChangeRequest request) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(EntityNotFoundException::new);
        orderTable.changeNumberOfGuests(request.getNumberOfGuests());
        return OrderTableResponse.of(orderTable);
    }
}