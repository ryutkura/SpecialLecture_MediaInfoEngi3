// パッケージ名はシンプルに、またはプロジェクトの基本パッケージに合わせて選択
// package mymusic;

public class MelodyEvaluationUtil {
    // 静的評価メソッドの定義

    public static int evaluateMelody(int[][][] population, int individualIndex) {
        int score = 0;
        int num_code = 4; // コードの数
        
        // 適切なコード（和音）を選択するための仮のコード配列
        int[] cMajor = {60, 64, 67}; // C, E, G
        int[] fMajor = {65, 69, 60}; // F, A, C
        int[] gMajor = {67, 71, 62}; // G, B, D
        
        // 強拍の位置の仮の設定
        int[] accentuatedBeats = {0, 2}; // 例えば1拍目と3拍目を強拍とする
        
        // 各コードに対する評価を行う
        for (int j = 0; j < num_code; j++) {
            int[] noteSequence = population[individualIndex][j]; // 個体の特定のコードに対応するメロディーライン
            int[] harmonyNotes = cMajor; // 仮にCメジャーコードを使用
    
            if (j == 1) {
                harmonyNotes = fMajor; // Fコードの場合
            } else if (j == 2) {
                harmonyNotes = gMajor; // Gコードの場合
            }
    
            score += evaluateMelodyFinalNote(noteSequence, harmonyNotes);
            score += evaluateMelodyConnection(noteSequence);
            score += evaluateStrongBeatConformity(noteSequence, harmonyNotes, accentuatedBeats);
            score += evaluatePassingTone(noteSequence, harmonyNotes);
            score += evaluateOrnamentalTone(noteSequence, harmonyNotes);
        }
    
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
// public static int evaluatePassingTone(int[] noteSequence) {
//     int score = 0;
//     // 評価ロジックを実装
//     return score;
// }
public static int evaluatePassingTone(int[] noteSequence, int[] chordNotes) {
    int score = 0;
    for (int i = 1; i < noteSequence.length - 1; i++) {
        if (!isChordTone(noteSequence[i], chordNotes) && isChordTone(noteSequence[i - 1], chordNotes) && isChordTone(noteSequence[i + 1], chordNotes)) {
            if (noteSequence[i - 1] < noteSequence[i] && noteSequence[i] < noteSequence[i + 1]) {
                score++; // 上向きの経過音
            } else if (noteSequence[i - 1] > noteSequence[i] && noteSequence[i] > noteSequence[i + 1]) {
                score++; // 下向きの経過音
            }
        }
    }
    return score;
}


// 刺繍音の有無を評価するメソッド
// public static int evaluateOrnamentalTone(int[] noteSequence) {
//     int score = 0;
//     // 評価ロジックを実装
//     return score;
// }
public static int evaluateOrnamentalTone(int[] noteSequence, int[] chordNotes) {
    int score = 0;
    for (int i = 1; i < noteSequence.length - 1; i++) {
        if (isChordTone(noteSequence[i - 1], chordNotes) && !isChordTone(noteSequence[i], chordNotes) && isChordTone(noteSequence[i + 1], chordNotes)) {
            score++; // 刺繍音が存在
        }
    }
    return score;
}

private static boolean isChordTone(int note, int[] chordNotes) {
    for (int chordNote : chordNotes) {
        if (note == chordNote) {
            return true; // 与えられた音符が和音音符である
        }
    }
    return false;
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
