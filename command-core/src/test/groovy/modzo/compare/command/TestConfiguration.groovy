package modzo.compare.command

import modzo.compare.command.core.domain.item.commands.create.photo.PhotoUploader
import modzo.compare.query.core.domain.events.services.EventService
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Configuration

@Configuration
class TestConfiguration {
    @MockBean
    EventService eventService

    @MockBean
    PhotoUploader photoUploader
}
