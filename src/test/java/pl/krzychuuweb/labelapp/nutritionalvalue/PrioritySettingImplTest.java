package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrioritySettingImplTest {

    @Mock
    private PriorityChecker priorityChecker;

    @InjectMocks
    private PrioritySettingImpl prioritySetting;

}