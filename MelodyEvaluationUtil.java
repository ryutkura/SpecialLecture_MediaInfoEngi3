// パッケージ名はシンプルに、またはプロジェクトの基本パッケージに合わせて選択
// package mymusic;

public class MelodyEvaluationUtil {
    // 静的評価メソッドの定義

    public static int evaluateMelody(int[][][] population, int individualIndex) {
        int score = 0;
        // ここで各評価メソッドを呼び出して合計点を計算
        // 例: score += evaluateChordConformity(melody, chordNotes);
        score += evaluateMelodyFinalNote(int[] noteSequence, int[] harmonyNotes);
        score += evaluateMelodyConnection(int[] noteSequence);
        score += evaluateStrongBeatConformity(int[] noteSequence, int[] harmonyNotes, int[] accentuatedBeats);
        // 例: score += evaluateChordConformity(melody, chordNotes);
        // 例: score += evaluateChordConformity(melody, chordNotes);
        // 例: score += evaluateChordConformity(melody, chordNotes);
        return score;
    }

    // その他の静的メソッド...
    // // コードへの適合度を評価するメソッド
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

    // メロディーラインの最終音の音高を評価するメソッド
    // public static int evaluateMelodyFinalNote(int[] melody, int[] chordNotes) {
    //     int lastNote = melody[melody.length - 1];
    //     for (int chordNote : chordNotes) {
    //         if (lastNote == chordNote) {
    //             return 1; // 高評価
    //         }
    //     }
    //     return -1; // 低評価
    // }

    // // メロディーラインのつながりを評価するメソッド
    // public static int evaluateMelodyConnection(int[] melody) {
    //     int score = 0;
    //     for (int i = 0; i < melody.length - 1; i++) {
    //         int diff = Math.abs(melody[i] - melody[i + 1]);
    //         if (diff <= 2) {
    //             score += 1; // 連続している場合に加点
    //         } else if (diff > 7) {
    //             score -= 5; // 不自然な変化に減点
    //         }
    //     }
    //     return score;
    // }

    // // 経過音の有無を評価するメソッド
    // public static int evaluatePassingTone(int[] melody) {
    //     int score = 0;
    //     // 評価ロジックを実装
    //     return score;
    // }

    // // 刺繍音の有無を評価するメソッド
    // public static int evaluateOrnamentalTone(int[] melody) {
    //     int score = 0;
    //     // 評価ロジックを実装
    //     return score;
    // }

    // // 強拍でのコード内音を評価するメソッド
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

    // メロディーラインの最終音の音高を評価するメソッド
public static int evaluateMelodyFinalNote(int[] noteSequence, int[] harmonyNotes) {
    int lastNote = noteSequence[noteSequence.length - 1];
    for (int harmonyNote : harmonyNotes) {
        if (lastNote == harmonyNote) {
            return 1; // 高評価
        }
    }
    return -1; // 低評価
}

// メロディーラインのつながりを評価するメソッド
public static int evaluateMelodyConnection(int[] noteSequence) {
    int score = 0;
    for (int i = 0; i < noteSequence.length - 1; i++) {
        int diff = Math.abs(noteSequence[i] - noteSequence[i + 1]);
        if (diff <= 2) {
            score += 1; // 連続している場合に加点
        } else if (diff > 7) {
            score -= 5; // 不自然な変化に減点
        }
    }
    return score;
}

// 経過音の有無を評価するメソッド
public static int evaluatePassingTone(int[] noteSequence) {
    int score = 0;
    // 評価ロジックを実装
    return score;
}

// 刺繍音の有無を評価するメソッド
public static int evaluateOrnamentalTone(int[] noteSequence) {
    int score = 0;
    // 評価ロジックを実装
    return score;
}

// 強拍でのコード内音を評価するメソッド
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
