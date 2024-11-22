package org.booking.service;

public class PaginationHandler {

/*    private final Scanner scanner;
    public PaginationHandler(Scanner scanner) {
        this.scanner = scanner;
    }


    public int calculateTotalPages(int roomsPerPage, int totalRooms) {
        return (int) Math.ceil((double) totalRooms / roomsPerPage);
    }


    void displayRoomPage(int currentPage, int totalPages, List<Room> roomList, int roomsPerPage) {
        int start = (currentPage - 1) * roomsPerPage;
        int end = Math.min(start + roomsPerPage, roomList.size());

        System.out.printf("\nPage %d of %d%n", currentPage, totalPages);
        for (int i = start; i < end; i++) {
            displayRoomDetails(roomList.get(i));
        }
    }


    private void displayRoomDetails(Room room) {
        System.out.println("""
                Room ID: %s,
                Status: %s,
                Price: %s
                """.formatted(
                room.getRoomId(),
                room.getStatus().name(),
                room.getRoomPrice()
        ));
    }


    String getNavigationAction() {
        System.out.println("Enter 'n' for next, 'p' for previous");
        return scanner.next();
    }


    public boolean isNextPageAction(int currentPage, int totalPages, String action) {
        if (currentPage >= totalPages && action.equalsIgnoreCase("n")) {
            System.out.printf("You are on the last page: %d", currentPage);
            return false;
        }
        return action.equalsIgnoreCase("n");
    }


    boolean isPreviousPageAction(int currentPage, String action) {
        if (currentPage <= 1 && action.equalsIgnoreCase("p")) {
            System.out.printf("You are on the first page: %d", currentPage);
            return false;
        }
        return action.equalsIgnoreCase("p");
    }*/
}

