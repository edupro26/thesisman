package pt.ul.fc.css.thesisman.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.dtos.RoomDTO;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Room;
import pt.ul.fc.css.thesisman.business.repositories.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<RoomDTO> findRoomByName(String roomName) {
        Optional<Room> room = roomRepository.findByRoom(roomName);

        return room.map(Room::toDTO);
    }

    public List<RoomDTO> findAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        List <RoomDTO> roomDTOs = new ArrayList<>();

        for (Room room : rooms) {
            roomDTOs.add(room.toDTO());
        }

        return roomDTOs;
    }
}
