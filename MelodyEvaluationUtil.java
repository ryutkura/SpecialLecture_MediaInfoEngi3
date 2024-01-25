// �p�b�P�[�W���̓V���v���ɁA�܂��̓v���W�F�N�g�̊�{�p�b�P�[�W�ɍ��킹�đI��
// package mymusic;

public class MelodyEvaluationUtil {
    // �ÓI�]�����\�b�h�̒�`

    public static int evaluateMelody(int[][][] population, int individualIndex) {
        int score = 0;
        // �����Ŋe�]�����\�b�h���Ăяo���č��v�_���v�Z
        // ��: score += evaluateChordConformity(melody, chordNotes);
        score += evaluateMelodyFinalNote(int[] noteSequence, int[] harmonyNotes);
        score += evaluateMelodyConnection(int[] noteSequence);
        score += evaluateStrongBeatConformity(int[] noteSequence, int[] harmonyNotes, int[] accentuatedBeats);
        // ��: score += evaluateChordConformity(melody, chordNotes);
        // ��: score += evaluateChordConformity(melody, chordNotes);
        // ��: score += evaluateChordConformity(melody, chordNotes);
        return score;
    }

    // ���̑��̐ÓI���\�b�h...
    // // �R�[�h�ւ̓K���x��]�����郁�\�b�h
    // public static int evaluateChordConformity(int[] melody, int[] chordNotes) {
    //     int conformingNotes = 0;
    //     for (int note : melody) {
    //         for (int chordNote : chordNotes) {
    //             if (note == chordNote) {
    //                 conformingNotes++;
    //                 break;
    //             }
    //         }
    //     }
    //     return conformingNotes;
    // }

    // �����f�B�[���C���̍ŏI���̉�����]�����郁�\�b�h
    // public static int evaluateMelodyFinalNote(int[] melody, int[] chordNotes) {
    //     int lastNote = melody[melody.length - 1];
    //     for (int chordNote : chordNotes) {
    //         if (lastNote == chordNote) {
    //             return 1; // ���]��
    //         }
    //     }
    //     return -1; // ��]��
    // }

    // // �����f�B�[���C���̂Ȃ����]�����郁�\�b�h
    // public static int evaluateMelodyConnection(int[] melody) {
    //     int score = 0;
    //     for (int i = 0; i < melody.length - 1; i++) {
    //         int diff = Math.abs(melody[i] - melody[i + 1]);
    //         if (diff <= 2) {
    //             score += 1; // �A�����Ă���ꍇ�ɉ��_
    //         } else if (diff > 7) {
    //             score -= 5; // �s���R�ȕω��Ɍ��_
    //         }
    //     }
    //     return score;
    // }

    // // �o�߉��̗L����]�����郁�\�b�h
    // public static int evaluatePassingTone(int[] melody) {
    //     int score = 0;
    //     // �]�����W�b�N������
    //     return score;
    // }

    // // �h�J���̗L����]�����郁�\�b�h
    // public static int evaluateOrnamentalTone(int[] melody) {
    //     int score = 0;
    //     // �]�����W�b�N������
    //     return score;
    // }

    // // �����ł̃R�[�h������]�����郁�\�b�h
    // public static int evaluateStrongBeatConformity(int[] melody, int[] chordNotes, int[] strongBeats) {
    //     int score = 0;
    //     for (int beat : strongBeats) {
    //         if (beat < melody.length) {
    //             for (int chordNote : chordNotes) {
    //                 if (melody[beat] == chordNote) {
    //                     score += 1;
    //                     break;
    //                 }
    //             }
    //         }
    //     }
    //     return score;
    // }

    // �����f�B�[���C���̍ŏI���̉�����]�����郁�\�b�h
public static int evaluateMelodyFinalNote(int[] noteSequence, int[] harmonyNotes) {
    int lastNote = noteSequence[noteSequence.length - 1];
    for (int harmonyNote : harmonyNotes) {
        if (lastNote == harmonyNote) {
            return 1; // ���]��
        }
    }
    return -1; // ��]��
}

// �����f�B�[���C���̂Ȃ����]�����郁�\�b�h
public static int evaluateMelodyConnection(int[] noteSequence) {
    int score = 0;
    for (int i = 0; i < noteSequence.length - 1; i++) {
        int diff = Math.abs(noteSequence[i] - noteSequence[i + 1]);
        if (diff <= 2) {
            score += 1; // �A�����Ă���ꍇ�ɉ��_
        } else if (diff > 7) {
            score -= 5; // �s���R�ȕω��Ɍ��_
        }
    }
    return score;
}

// �o�߉��̗L����]�����郁�\�b�h
public static int evaluatePassingTone(int[] noteSequence) {
    int score = 0;
    // �]�����W�b�N������
    return score;
}

// �h�J���̗L����]�����郁�\�b�h
public static int evaluateOrnamentalTone(int[] noteSequence) {
    int score = 0;
    // �]�����W�b�N������
    return score;
}

// �����ł̃R�[�h������]�����郁�\�b�h
public static int evaluateStrongBeatConformity(int[] noteSequence, int[] harmonyNotes, int[] accentuatedBeats) {
    int score = 0;
    for (int beat : accentuatedBeats) {
        if (beat < noteSequence.length) {
            for (int harmonyNote : harmonyNotes) {
                if (noteSequence[beat] == harmonyNote) {
                    score += 1;
                    break;
                }
            }
        }
    }
    return score;
}

}
