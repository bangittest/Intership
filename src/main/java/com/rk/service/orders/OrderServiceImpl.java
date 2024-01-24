package com.rk.service.orders;

import com.rk.dto.request.orders.OrdersRequestDTO;
import com.rk.dto.request.receiver.ReceiverRequestDTO;
import com.rk.dto.request.supplier.SupplierRequestDTO;
import com.rk.dto.response.orders.OrderResponseDTO;
import com.rk.exception.CustomException;
import com.rk.model.*;
import com.rk.repository.*;
import com.rk.service.warehouse.WareHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ReceiverRepository receiverRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private WareHouseService wareHouseService;
    @Override
    public List<OrderResponseDTO> findAll() {
        List<Orders>list=orderRepository.findAll();
        return list.stream().map(OrderResponseDTO::new).toList();
    }

    @Override
    public List<OrderResponseDTO> searchByWareHouseId(String warehouseCode) {
        List<Orders>list=orderRepository.findAllByIdContainsIgnoreCase(warehouseCode);
        return list.stream().map(OrderResponseDTO::new).toList();
    }

    @Override
    public List<OrderResponseDTO> searchByPhoneNumber(String phoneNumber) {
               List<Orders>list=orderRepository.findByReceiverPhoneNumberContainingOrSupplierPhoneNumberContaining(phoneNumber,phoneNumber);
        return list.stream().map(OrderResponseDTO::new).toList();
    }

    @Override
    public List<OrderResponseDTO> filterByStatus(Integer status) {
        List<Orders> orders = orderRepository.findByStatus(status);
        return orders.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> filterByWarehouseCode(String warehouseCode) {
        List<Orders> orders = orderRepository.findByHistories_Warehouse_Id(warehouseCode);
        return orders.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Page<OrderResponseDTO> paginate(Pageable pageable) {
        Page<Orders>orders=orderRepository.findAll(pageable);
        return orders.map(OrderResponseDTO::new);
    }


    @Override
    @org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
    public OrderResponseDTO createOrder(OrdersRequestDTO orderRequestDTO, Long userId) throws CustomException {

        try {
            Orders newOrder = new Orders();
            String orderCode = generateOrderCode();
            newOrder.setId(orderCode);

            newOrder.setCreatedAt(new Date());
            newOrder.setStatus(0);
            newOrder.setDeliveredAt(new Date());
            newOrder.setStoredAt(new Date());
            newOrder.setReturnCause(new Date());
            newOrder.setNumberFailures(orderRequestDTO.getNumberFailures());

            SupplierRequestDTO supplierDTO = orderRequestDTO.getSupplierRequestDTO();
            Supplier supplier = new Supplier();
            supplier.setSupplierName(supplierDTO.getName());
            supplier.setAddress(supplierDTO.getAddress());
            supplier.setPhoneNumber(supplierDTO.getPhoneNumber());
            supplier.setEmail(supplierDTO.getEmail());
            supplierRepository.save(supplier);
            newOrder.setSupplier(supplier);

            ReceiverRequestDTO receiverDTO = orderRequestDTO.getReceiverRequestDTO();
            Receiver receiver = new Receiver();
            receiver.setReceiverName(receiverDTO.getName());
            receiver.setAddress(receiverDTO.getAddress());
            receiver.setPhoneNumber(receiverDTO.getPhoneNumber());
            receiver.setEmail(receiverDTO.getEmail());
            if (receiverDTO.getLatitude() != null && receiverDTO.getLongitude() != null) {
                receiver.setLatitude(Double.valueOf(receiverDTO.getLatitude()));
                receiver.setLongitude(Double.valueOf(receiverDTO.getLongitude()));
            }
            receiverRepository.save(receiver);
            newOrder.setReceiver(receiver);
            String warehouseCode=orderRequestDTO.getWarehouseId();

            Orders savedOrder = orderRepository.save(newOrder);
            createOrderHistory(savedOrder, userId,warehouseCode);
            return new OrderResponseDTO(savedOrder);
        } catch (Exception e) {
            log.error("Ngoại lệ xảy ra trong quá trình tạo đơn hàng: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new CustomException("Đã xảy ra lỗi trong quá trình tạo đơn hàng");
        }
    }
        public String generateOrderCode() {
            String PREFIX = "DH";
            DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
            LocalDate today = LocalDate.now();
            String datePart = today.format(DATE_FORMATTER);
            Long maxId = orderRepository.findMaxIdByDate(datePart);
            Long nextSequencePart = (maxId != null) ? maxId + 1 : 1;
            String sequencePart = String.format("%05d", nextSequencePart);
            System.out.println(sequencePart);
            return PREFIX + "-" + datePart + "-" + sequencePart;
        }
    @org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
    public void createOrderHistory(Orders order, Long userId,String warehouseCode) throws CustomException {
        try {
        History orderHistory = new History();
        orderHistory.setUserTime(new Date());
        User user=userRepository.findById(userId).orElseThrow(()->new CustomException("User not found"));
       if (user==null){
           orderHistory.setUserId(1L);
       }else {
           orderHistory.setUserId(user.getId());
       }
        orderHistory.setOrders(order);
        Warehouse warehouse = warehouseRepository.findById(warehouseCode).orElseThrow(()->new CustomException("Warehouse not found"));
        orderHistory.setWarehouse(warehouse);
        orderHistory.setStatus(order.getStatus());
        historyRepository.save(orderHistory);
        } catch (Exception e) {
        log.error("Đã xảy ra ngoại lệ trong quá trình tạo lịch sử đơn hàng: {}", e.getMessage());
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        throw new CustomException("Lỗi trong quá trình tạo lịch sử đơn hàng");
        }
    }

    @Override
    public Optional<Orders> getOrderById(String orderId) {
        return Optional.empty();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUpdate(Long userId) throws CustomException {
      try {
          User user=userRepository.findById(userId).orElseThrow(()->new CustomException("User not found"));
          List<Orders>list=orderRepository.findAllByStatusFalseOrderByCreatedAtAsc();
          for (Orders orders1:list) {
              orders1.setWarehouse(orders1.getWarehouse());
              orders1.setStatus(1);
              orders1.setCreatedAt(new Date());
              orderRepository.save(orders1);
          }
          List<History>histories=historyRepository.findAll();

          for (History history:histories) {
              if (user==null){
                history.setUserId(-1L);
              }
              history.setWarehouse(history.getWarehouse());
              history.setStatus(1);
              history.setUserTime(new Date());
              historyRepository.save(history);
          }
      }catch (Exception e) {
          log.error("Đã xảy ra ngoại lệ trong quá trình tạo lịch sử đơn hàng: {}", e.getMessage());
          TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
          throw new CustomException("Lỗi trong quá trình tạo lịch sử đơn hàng");
      }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Orders> findTop100ByStatusOrderByCreatedAtAsc(Long userId) throws CustomException {
     try {
         List<Orders> newOrders = orderRepository.findTop100ByStatusOrderByCreatedAtAsc(0);
         // Lấy danh sách các kho và thông tin số lượng còn trống
         List<Warehouse> warehouses = getWarehouseListWithAvailability();
         // Duyệt qua từng đơn hàng mới
         for (Orders orders : newOrders) {
             // Tìm kho gần nhất
             Warehouse nearestWarehouse = findNearestWarehouse(orders, warehouses);
             // Cập nhật thông tin đơn hàng
             orders.setStatus(1);
             orders.setStoredAt(new Date());
             orders.setWarehouse(nearestWarehouse);
             //orders.setWarehouse(warehouse.getId());
//        orders.setWarehouse(warehouse.getWarehouseName());
             // Lưu lịch sử
             User user=userRepository.findById(userId).orElse(null);

             History history = new History();
             if (user==null){
                 history.setUserId(-1L);
             }
             history.setUserId(userId);
             history.setUserTime(new Date());
             history.setOrders(orders);
//        history.setWarehouseId(warehouse.getId());
             history.setWarehouse(nearestWarehouse);
             history.setStatus(orders.getStatus());

             // Giả định: Sử dụng dịch vụ lịch sử để lưu lịch sử
             historyRepository.save(history);
         }
         // Cập nhật thông tin số lượng còn trống cho các kho
         updateWarehouseAvailability(warehouses);
         return newOrders;
     }catch (Exception e) {
         log.error("Đã xảy ra ngoại lệ trong quá trình tạo lịch sử đơn hàng: {}", e.getMessage());
         TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
         throw new CustomException("Lỗi trong quá trình tạo lịch sử đơn hàng");
     }
    }



    // Hàm lấy danh sách các kho và thông tin số lượng còn trống
    private List<Warehouse> getWarehouseListWithAvailability() {
        // Sử dụng dịch vụ kho để lấy danh sách
        List<Warehouse> warehouses = warehouseRepository.findAll();

        // Cập nhật thông tin số lượng còn trống cho mỗi kho
        return warehouses.stream()
                .peek(warehouse -> {
                    try {
                        warehouse.setCapacity(getWarehouseAvailability(warehouse.getId()));
                    } catch (CustomException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    // Hàm tìm kho gần nhất
    private Warehouse findNearestWarehouse(Orders orders, List<Warehouse> warehouses) {
        // Sắp xếp các kho theo khoảng cách tới địa chỉ nhận hàng
        warehouses.sort(Comparator.comparingDouble(warehouse ->
                calculateDistance(orders.getReceiver().getLatitude(),orders.getReceiver().getLongitude(), warehouse.getLatitude(), warehouse.getLongitude())));
        // Lấy kho đầu tiên (gần nhất)
        return warehouses.get(0);
    }
    private static final double EARTH_RADIUS = 6371.0; // Bán kính Trái đất (đơn vị: km)

    private double calculateDistance( double lat1, double lon1, double lat2, double lon2) {
        // Chuyển đổi độ sang radian
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Tính chênh lệch giữa các tọa độ
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Khoảng cách giữa hai điểm
        double distance = EARTH_RADIUS * c;

        return distance;
    }
    // Hàm cập nhật thông tin số lượng còn trống cho các kho
    private void updateWarehouseAvailability(List<Warehouse> warehouses) throws CustomException {
        for (Warehouse warehouse : warehouses) {
            int updatedAvailability = calculateUpdatedAvailability(warehouse.getId());
            warehouse.setCapacity(updatedAvailability);
            wareHouseService.updateAvailability(warehouse);
        }
    }

    // Hàm lấy thông tin số lượng còn trống của kho (giả định)
    private int getWarehouseAvailability(String warehouseId) throws CustomException {
        // Giả định: Sử dụng dịch vụ kho để lấy thông tin số lượng còn trống
        return wareHouseService.getAvailability(warehouseId);
    }

    // Hàm tính thông tin số lượng còn trống sau khi cập nhật (giả định)
    private int calculateUpdatedAvailability(String warehouseId) throws CustomException {
        //  Sử dụng dịch vụ kho để lấy thông tin số lượng còn trống
        int currentAvailability = wareHouseService.getAvailability(warehouseId);

        //  Thực hiện các bước tính toán để cập nhật số lượng còn trống (ví dụ: giảm đi 10 đơn vị)
        int updatedAvailability = currentAvailability - 10;

        return updatedAvailability;
    }
    @Autowired
    private ReasonsRepository reasonsRepository;

    @Override
    public void confirmDelivery(String orderId, Integer statusOrder, Long reasonId) throws CustomException {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("SYSS-1100 ,Order not found"));
        if (!(order.getStatus() == 1 || order.getStatus() == 3)) {
            throw new CustomException("SYSS-1101, Invalid order status for delivery confirmation");
        }

        order.setStatus(statusOrder==1 ? 2 : 3);
        if (statusOrder!=1) {
            order.setNumberFailures(order.getNumberFailures() + 1);
        }
        Set<Reason>reasons=new HashSet<>();
        order.getReasons().forEach(reason ->reasons.add(reasonsRepository.findById(reasonId).orElse(null)) );
        order.setReasons(reasons);
        orderRepository.save(order);
        History history = new History();
        history.setUserId(-1L);
        history.setUserTime(new Date());
        history.setOrders(order);
        Warehouse warehouse=warehouseRepository.findById(order.getWarehouse().getId()).orElse(null);
        history.setWarehouse(warehouse);
        history.setStatus(order.getStatus());

        historyRepository.save(history);

//        // If successful delivery, update warehouse availability
//        if (isSuccessful) {
//            updateWarehouseAvailability(order.getWarehouse(),reasonId);
//        }
    }




    @Override
    @Transactional(rollbackFor =Exception.class)
    public void handleReturns(Long userId) {
      try {
          List<Orders> ordersToHandle = orderRepository.findTop100ByStatusOrderByCreatedAtAsc(3);
          for (Orders order : ordersToHandle) {
              int currentCapacity = order.getWarehouse().getCapacity();
              int updatedCapacity = currentCapacity + 1;
              order.getWarehouse().setCapacity(updatedCapacity);
              warehouseRepository.save(order.getWarehouse());
              order.setDeliveredAt(new Date());
              order.setStatus(4);
              orderRepository.save(order);
              History history = new History();
              User user=userRepository.findById(userId).orElse(null);
              if (user != null) {
                  history.setUserId(user.getId());
              }else {
                  history.setUserId(-1L);
              }
              history.setUserTime(new Date());
              history.setOrders(order);
              Warehouse warehouse=warehouseRepository.findById(order.getWarehouse().getId()).orElse(null);
              history.setWarehouse(warehouse);
              history.setStatus(order.getStatus());
              historyRepository.save(history);

              // Send emails
//              sendEmailToReceiver(order);
//              sendEmailToSupplier(order);
          }
      }catch (Exception e) {
          log.error("Đã xảy ra ngoại lệ trong quá trình tạo lịch sử đơn hàng: {}", e.getMessage());
          TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      }
    }
//    private void sendEmailToReceiver(Orders order) {
////        emailService.sendEmail(order.getReceiver().getEmail(), "Subject", "Email content");
//    }
//    private void sendEmailToSupplier(Orders order) {
////        emailService.sendEmail(order.getSupplier().getEmail(), "Subject", "Email content");
//    }
}



