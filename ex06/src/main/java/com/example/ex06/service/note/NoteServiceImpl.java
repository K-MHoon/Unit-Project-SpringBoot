package com.example.ex06.service.note;

import com.example.ex06.dto.note.NoteDTO;
import com.example.ex06.entity.note.Note;
import com.example.ex06.repository.note.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public Long register(NoteDTO noteDTO) {

        Note note = dtoToEntity(noteDTO);

        log.info("====================");
        log.info("note = {}", note);

        noteRepository.save(note);

        return note.getNum();
    }

    @Override
    @Transactional(readOnly = true)
    public NoteDTO get(Long num) {

        Note result = noteRepository.getWithWriter(num).orElseThrow(() ->
                new EntityNotFoundException("해당하는 Note가 존재하지 않습니다."));

        return entityToDTO(result);
    }

    @Override
    public void modify(NoteDTO noteDTO) {
        Long num = noteDTO.getNum();
        Note note = noteRepository.findById(num).orElseThrow(() -> new EntityNotFoundException("해당하는 Note가 존재하지 않습니다."));
        note.updateContent(noteDTO.getContent());
        note.updateTitle(noteDTO.getTitle());
        noteRepository.save(note);
    }

    @Override
    public void remove(Long num) {
        noteRepository.deleteById(num);
    }

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {
        List<Note> noteList = noteRepository.getList(writerEmail);

        return noteList.stream().map(note -> entityToDTO(note)).collect(Collectors.toList());
    }
}
