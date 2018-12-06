(ns adventofcode.2015.day16
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))


(def input "Sue 1: children: 1, cars: 8, vizslas: 7\nSue 2: akitas: 10, perfumes: 10, children: 5\nSue 3: cars: 5, pomeranians: 4, vizslas: 1\nSue 4: goldfish: 5, children: 8, perfumes: 3\nSue 5: vizslas: 2, akitas: 7, perfumes: 6\nSue 6: vizslas: 0, akitas: 1, perfumes: 2\nSue 7: perfumes: 8, cars: 4, goldfish: 10\nSue 8: perfumes: 7, children: 2, cats: 1\nSue 9: pomeranians: 3, goldfish: 10, trees: 10\nSue 10: akitas: 7, trees: 8, pomeranians: 4\nSue 11: goldfish: 1, perfumes: 4, cars: 6\nSue 12: samoyeds: 6, trees: 6, perfumes: 2\nSue 13: akitas: 10, pomeranians: 0, vizslas: 2\nSue 14: cars: 2, perfumes: 3, children: 4\nSue 15: goldfish: 2, children: 8, cars: 5\nSue 16: goldfish: 9, cars: 0, vizslas: 5\nSue 17: cats: 5, trees: 6, perfumes: 6\nSue 18: cars: 0, perfumes: 8, pomeranians: 7\nSue 19: trees: 2, goldfish: 5, perfumes: 4\nSue 20: akitas: 4, vizslas: 4, trees: 0\nSue 21: pomeranians: 7, trees: 0, goldfish: 10\nSue 22: cars: 4, vizslas: 0, perfumes: 3\nSue 23: vizslas: 8, trees: 1, akitas: 2\nSue 24: children: 7, trees: 0, akitas: 1\nSue 25: goldfish: 3, akitas: 2, trees: 2\nSue 26: pomeranians: 4, vizslas: 4, samoyeds: 2\nSue 27: cars: 0, trees: 8, akitas: 5\nSue 28: perfumes: 6, cats: 0, cars: 2\nSue 29: trees: 7, akitas: 1, vizslas: 1\nSue 30: perfumes: 9, cars: 9, trees: 10\nSue 31: pomeranians: 5, akitas: 9, samoyeds: 1\nSue 32: pomeranians: 10, vizslas: 5, goldfish: 5\nSue 33: vizslas: 2, akitas: 3, trees: 7\nSue 34: goldfish: 10, perfumes: 0, samoyeds: 7\nSue 35: akitas: 6, cats: 7, perfumes: 10\nSue 36: pomeranians: 8, vizslas: 7, akitas: 6\nSue 37: goldfish: 2, cars: 10, children: 7\nSue 38: goldfish: 2, perfumes: 3, cars: 7\nSue 39: trees: 9, vizslas: 10, cars: 5\nSue 40: goldfish: 1, pomeranians: 0, trees: 2\nSue 41: trees: 2, goldfish: 6, vizslas: 3\nSue 42: akitas: 1, cars: 3, vizslas: 3\nSue 43: akitas: 1, pomeranians: 1, vizslas: 3\nSue 44: goldfish: 7, akitas: 3, vizslas: 10\nSue 45: akitas: 8, samoyeds: 8, goldfish: 2\nSue 46: trees: 0, vizslas: 4, cars: 9\nSue 47: cars: 9, trees: 10, perfumes: 4\nSue 48: akitas: 0, vizslas: 5, perfumes: 4\nSue 49: goldfish: 9, trees: 1, cars: 4\nSue 50: goldfish: 2, perfumes: 5, cars: 2\nSue 51: samoyeds: 1, goldfish: 2, perfumes: 7\nSue 52: cars: 0, perfumes: 4, goldfish: 8\nSue 53: goldfish: 9, vizslas: 2, akitas: 9\nSue 54: trees: 1, goldfish: 9, children: 5\nSue 55: cars: 0, akitas: 5, trees: 4\nSue 56: trees: 4, samoyeds: 5, children: 9\nSue 57: children: 0, vizslas: 8, cars: 3\nSue 58: trees: 4, pomeranians: 5, akitas: 5\nSue 59: vizslas: 10, cats: 3, children: 2\nSue 60: cats: 6, vizslas: 2, cars: 2\nSue 61: akitas: 1, vizslas: 0, children: 4\nSue 62: akitas: 4, trees: 9, children: 10\nSue 63: pomeranians: 6, vizslas: 6, cars: 4\nSue 64: perfumes: 8, pomeranians: 1, children: 8\nSue 65: perfumes: 3, goldfish: 6, trees: 5\nSue 66: goldfish: 10, akitas: 8, vizslas: 4\nSue 67: vizslas: 10, samoyeds: 3, trees: 2\nSue 68: samoyeds: 4, cars: 7, perfumes: 3\nSue 69: perfumes: 2, goldfish: 0, trees: 2\nSue 70: trees: 8, vizslas: 7, akitas: 6\nSue 71: cars: 2, children: 7, perfumes: 3\nSue 72: cars: 1, akitas: 9, perfumes: 0\nSue 73: vizslas: 4, akitas: 7, cars: 5\nSue 74: samoyeds: 3, cars: 3, akitas: 2\nSue 75: trees: 2, cars: 1, vizslas: 7\nSue 76: samoyeds: 9, perfumes: 1, trees: 6\nSue 77: trees: 6, perfumes: 10, cars: 7\nSue 78: trees: 0, children: 8, vizslas: 5\nSue 79: vizslas: 0, trees: 0, samoyeds: 1\nSue 80: trees: 6, goldfish: 8, perfumes: 0\nSue 81: samoyeds: 8, pomeranians: 6, akitas: 5\nSue 82: vizslas: 6, perfumes: 9, akitas: 4\nSue 83: cats: 0, vizslas: 3, pomeranians: 10\nSue 84: cars: 4, perfumes: 6, samoyeds: 5\nSue 85: vizslas: 7, trees: 5, goldfish: 7\nSue 86: goldfish: 2, trees: 2, vizslas: 1\nSue 87: trees: 6, goldfish: 10, pomeranians: 4\nSue 88: vizslas: 1, akitas: 0, perfumes: 8\nSue 89: goldfish: 8, akitas: 3, vizslas: 7\nSue 90: vizslas: 9, akitas: 7, perfumes: 9\nSue 91: children: 7, cars: 7, trees: 9\nSue 92: vizslas: 10, akitas: 8, goldfish: 1\nSue 93: goldfish: 7, vizslas: 2, pomeranians: 0\nSue 94: cats: 2, samoyeds: 6, pomeranians: 3\nSue 95: samoyeds: 4, children: 4, pomeranians: 10\nSue 96: pomeranians: 9, cats: 1, goldfish: 3\nSue 97: trees: 1, akitas: 6, goldfish: 1\nSue 98: vizslas: 7, akitas: 2, perfumes: 7\nSue 99: pomeranians: 6, perfumes: 2, trees: 1\nSue 100: cars: 3, children: 9, trees: 10\nSue 101: children: 0, perfumes: 0, vizslas: 3\nSue 102: cars: 4, goldfish: 5, children: 2\nSue 103: pomeranians: 3, perfumes: 7, cats: 8\nSue 104: akitas: 0, perfumes: 5, vizslas: 5\nSue 105: akitas: 7, vizslas: 2, samoyeds: 8\nSue 106: goldfish: 7, perfumes: 0, cats: 8\nSue 107: cats: 6, pomeranians: 9, cars: 6\nSue 108: akitas: 3, vizslas: 10, cats: 5\nSue 109: akitas: 10, perfumes: 2, cars: 7\nSue 110: goldfish: 7, pomeranians: 1, trees: 1\nSue 111: akitas: 10, samoyeds: 6, vizslas: 6\nSue 112: cats: 6, akitas: 7, trees: 9\nSue 113: akitas: 1, trees: 9, vizslas: 8\nSue 114: vizslas: 2, cats: 1, cars: 4\nSue 115: akitas: 0, trees: 5, goldfish: 7\nSue 116: goldfish: 2, trees: 10, akitas: 2\nSue 117: cars: 4, goldfish: 10, perfumes: 5\nSue 118: cars: 5, perfumes: 6, trees: 0\nSue 119: perfumes: 5, vizslas: 1, cats: 0\nSue 120: perfumes: 8, akitas: 9, vizslas: 4\nSue 121: samoyeds: 2, vizslas: 7, perfumes: 6\nSue 122: children: 6, trees: 9, perfumes: 2\nSue 123: cars: 7, akitas: 0, pomeranians: 0\nSue 124: akitas: 7, cats: 8, vizslas: 5\nSue 125: goldfish: 3, trees: 1, cars: 4\nSue 126: cars: 4, perfumes: 3, akitas: 0\nSue 127: children: 10, vizslas: 5, akitas: 9\nSue 128: akitas: 3, samoyeds: 2, cats: 8\nSue 129: cats: 8, akitas: 1, vizslas: 8\nSue 130: trees: 4, cars: 6, perfumes: 6\nSue 131: akitas: 7, perfumes: 6, goldfish: 9\nSue 132: akitas: 6, vizslas: 7, trees: 1\nSue 133: akitas: 5, vizslas: 7, children: 9\nSue 134: cars: 8, goldfish: 4, pomeranians: 4\nSue 135: samoyeds: 1, pomeranians: 6, akitas: 4\nSue 136: perfumes: 10, goldfish: 1, cars: 3\nSue 137: cars: 3, samoyeds: 6, vizslas: 7\nSue 138: samoyeds: 10, akitas: 3, perfumes: 4\nSue 139: perfumes: 10, vizslas: 2, goldfish: 7\nSue 140: samoyeds: 7, cars: 1, trees: 2\nSue 141: children: 6, cats: 5, cars: 9\nSue 142: cats: 0, trees: 1, akitas: 10\nSue 143: samoyeds: 4, cars: 0, children: 7\nSue 144: trees: 0, cars: 4, perfumes: 8\nSue 145: goldfish: 7, cars: 5, trees: 1\nSue 146: perfumes: 7, cars: 7, goldfish: 0\nSue 147: trees: 2, goldfish: 7, vizslas: 5\nSue 148: samoyeds: 8, perfumes: 1, trees: 0\nSue 149: vizslas: 2, samoyeds: 5, trees: 0\nSue 150: akitas: 4, perfumes: 4, pomeranians: 2\nSue 151: trees: 2, cars: 0, goldfish: 10\nSue 152: goldfish: 7, vizslas: 0, trees: 0\nSue 153: children: 9, cats: 0, pomeranians: 10\nSue 154: cars: 6, goldfish: 10, akitas: 5\nSue 155: perfumes: 9, trees: 2, akitas: 3\nSue 156: pomeranians: 9, perfumes: 5, cars: 9\nSue 157: akitas: 0, trees: 2, cars: 7\nSue 158: goldfish: 10, trees: 8, akitas: 7\nSue 159: akitas: 5, trees: 10, cars: 10\nSue 160: akitas: 3, trees: 5, cars: 8\nSue 161: samoyeds: 2, cars: 7, perfumes: 4\nSue 162: cars: 6, vizslas: 10, pomeranians: 5\nSue 163: cars: 10, perfumes: 6, vizslas: 9\nSue 164: pomeranians: 7, cars: 4, vizslas: 2\nSue 165: goldfish: 9, vizslas: 3, trees: 1\nSue 166: goldfish: 1, samoyeds: 3, trees: 1\nSue 167: vizslas: 4, goldfish: 7, cats: 5\nSue 168: children: 1, cars: 5, samoyeds: 7\nSue 169: trees: 1, samoyeds: 3, goldfish: 6\nSue 170: goldfish: 2, cars: 3, perfumes: 9\nSue 171: cars: 4, goldfish: 0, trees: 6\nSue 172: cats: 8, perfumes: 6, trees: 1\nSue 173: akitas: 9, goldfish: 7, cars: 10\nSue 174: vizslas: 2, trees: 0, akitas: 1\nSue 175: perfumes: 3, vizslas: 8, akitas: 4\nSue 176: perfumes: 0, akitas: 6, goldfish: 3\nSue 177: perfumes: 6, children: 1, goldfish: 10\nSue 178: cars: 5, vizslas: 3, children: 10\nSue 179: perfumes: 3, trees: 8, cats: 9\nSue 180: perfumes: 8, vizslas: 4, trees: 7\nSue 181: perfumes: 7, vizslas: 9, samoyeds: 4\nSue 182: vizslas: 9, trees: 4, pomeranians: 4\nSue 183: trees: 9, cars: 3, goldfish: 5\nSue 184: perfumes: 2, cars: 4, vizslas: 3\nSue 185: children: 10, akitas: 10, cats: 9\nSue 186: cars: 5, samoyeds: 0, trees: 0\nSue 187: trees: 2, goldfish: 3, cars: 4\nSue 188: goldfish: 3, vizslas: 1, cats: 6\nSue 189: trees: 2, pomeranians: 10, cars: 7\nSue 190: perfumes: 10, akitas: 3, samoyeds: 0\nSue 191: cats: 5, vizslas: 6, akitas: 6\nSue 192: samoyeds: 5, trees: 1, perfumes: 8\nSue 193: pomeranians: 0, akitas: 9, cats: 0\nSue 194: trees: 1, goldfish: 0, perfumes: 10\nSue 195: perfumes: 2, akitas: 7, cars: 5\nSue 196: perfumes: 5, samoyeds: 8, cars: 1\nSue 197: vizslas: 2, pomeranians: 9, trees: 1\nSue 198: trees: 8, vizslas: 6, children: 8\nSue 199: pomeranians: 4, cars: 7, vizslas: 5\nSue 200: trees: 0, perfumes: 10, akitas: 10\nSue 201: cats: 9, akitas: 4, vizslas: 0\nSue 202: goldfish: 9, pomeranians: 9, cats: 6\nSue 203: cars: 5, perfumes: 5, trees: 2\nSue 204: pomeranians: 7, children: 2, akitas: 6\nSue 205: samoyeds: 7, pomeranians: 7, children: 6\nSue 206: trees: 1, cars: 1, pomeranians: 4\nSue 207: goldfish: 2, perfumes: 5, trees: 0\nSue 208: perfumes: 2, samoyeds: 4, trees: 1\nSue 209: cars: 8, perfumes: 6, goldfish: 9\nSue 210: perfumes: 4, cars: 8, samoyeds: 3\nSue 211: perfumes: 2, cars: 8, trees: 9\nSue 212: trees: 7, perfumes: 2, akitas: 5\nSue 213: children: 3, goldfish: 5, vizslas: 0\nSue 214: akitas: 6, goldfish: 0, children: 0\nSue 215: trees: 8, akitas: 3, goldfish: 1\nSue 216: goldfish: 6, perfumes: 8, akitas: 3\nSue 217: children: 7, trees: 2, vizslas: 6\nSue 218: goldfish: 8, samoyeds: 4, pomeranians: 6\nSue 219: goldfish: 8, samoyeds: 0, children: 9\nSue 220: perfumes: 1, cars: 8, vizslas: 6\nSue 221: perfumes: 9, cars: 10, children: 10\nSue 222: perfumes: 9, vizslas: 1, trees: 0\nSue 223: goldfish: 1, akitas: 2, vizslas: 8\nSue 224: samoyeds: 8, akitas: 7, vizslas: 4\nSue 225: goldfish: 1, cars: 4, perfumes: 10\nSue 226: goldfish: 9, trees: 4, perfumes: 5\nSue 227: vizslas: 5, trees: 4, goldfish: 7\nSue 228: cars: 1, cats: 10, perfumes: 4\nSue 229: vizslas: 8, cars: 10, akitas: 4\nSue 230: cats: 1, children: 8, vizslas: 3\nSue 231: perfumes: 7, cats: 6, samoyeds: 7\nSue 232: cars: 3, children: 9, perfumes: 7\nSue 233: vizslas: 1, samoyeds: 2, children: 2\nSue 234: trees: 1, samoyeds: 8, children: 2\nSue 235: trees: 6, akitas: 9, goldfish: 7\nSue 236: children: 10, trees: 0, samoyeds: 8\nSue 237: pomeranians: 4, trees: 1, goldfish: 2\nSue 238: vizslas: 4, akitas: 2, cars: 0\nSue 239: goldfish: 9, cars: 10, perfumes: 4\nSue 240: perfumes: 3, vizslas: 6, trees: 6\nSue 241: pomeranians: 6, akitas: 4, trees: 2\nSue 242: cars: 8, perfumes: 5, children: 7\nSue 243: trees: 4, perfumes: 7, cars: 3\nSue 244: perfumes: 6, akitas: 1, vizslas: 7\nSue 245: akitas: 3, perfumes: 9, samoyeds: 0\nSue 246: pomeranians: 3, vizslas: 9, samoyeds: 1\nSue 247: cars: 0, goldfish: 7, cats: 2\nSue 248: trees: 5, goldfish: 6, perfumes: 3\nSue 249: trees: 0, pomeranians: 7, perfumes: 9\nSue 250: cars: 9, trees: 1, goldfish: 10\nSue 251: perfumes: 3, cars: 8, trees: 7\nSue 252: cars: 5, akitas: 7, trees: 8\nSue 253: perfumes: 7, akitas: 3, trees: 8\nSue 254: goldfish: 8, samoyeds: 1, vizslas: 7\nSue 255: perfumes: 3, cars: 4, children: 6\nSue 256: perfumes: 9, trees: 8, children: 7\nSue 257: trees: 8, children: 6, cars: 4\nSue 258: vizslas: 1, trees: 10, goldfish: 9\nSue 259: vizslas: 5, trees: 6, goldfish: 9\nSue 260: trees: 0, goldfish: 6, cars: 7\nSue 261: cars: 1, perfumes: 4, goldfish: 9\nSue 262: cars: 7, goldfish: 9, cats: 9\nSue 263: cars: 0, children: 5, goldfish: 8\nSue 264: cars: 2, akitas: 8, trees: 0\nSue 265: perfumes: 9, children: 8, samoyeds: 7\nSue 266: cats: 1, children: 1, vizslas: 10\nSue 267: vizslas: 8, children: 2, trees: 6\nSue 268: akitas: 10, vizslas: 3, cats: 2\nSue 269: children: 4, goldfish: 1, cats: 6\nSue 270: vizslas: 5, cars: 9, akitas: 9\nSue 271: vizslas: 5, children: 4, akitas: 3\nSue 272: cars: 1, goldfish: 0, vizslas: 0\nSue 273: goldfish: 10, samoyeds: 1, akitas: 2\nSue 274: goldfish: 10, children: 2, pomeranians: 0\nSue 275: children: 0, vizslas: 1, samoyeds: 6\nSue 276: children: 1, vizslas: 3, samoyeds: 1\nSue 277: perfumes: 4, cats: 6, children: 10\nSue 278: pomeranians: 7, goldfish: 3, cars: 4\nSue 279: perfumes: 5, goldfish: 9, trees: 7\nSue 280: goldfish: 6, trees: 5, perfumes: 8\nSue 281: cars: 2, akitas: 1, vizslas: 7\nSue 282: vizslas: 4, akitas: 3, children: 8\nSue 283: pomeranians: 8, akitas: 9, vizslas: 4\nSue 284: samoyeds: 10, trees: 10, pomeranians: 2\nSue 285: akitas: 9, perfumes: 7, goldfish: 6\nSue 286: akitas: 2, vizslas: 7, goldfish: 10\nSue 287: pomeranians: 8, cars: 6, samoyeds: 5\nSue 288: pomeranians: 1, trees: 0, goldfish: 0\nSue 289: trees: 10, samoyeds: 1, children: 0\nSue 290: cats: 10, samoyeds: 6, trees: 0\nSue 291: vizslas: 9, trees: 6, goldfish: 5\nSue 292: cats: 4, perfumes: 8, cars: 3\nSue 293: goldfish: 10, perfumes: 10, cats: 0\nSue 294: cats: 7, trees: 6, akitas: 4\nSue 295: vizslas: 8, cars: 1, akitas: 6\nSue 296: vizslas: 5, akitas: 10, trees: 1\nSue 297: pomeranians: 8, samoyeds: 5, vizslas: 4\nSue 298: perfumes: 10, children: 5, vizslas: 2\nSue 299: cars: 10, akitas: 7, cats: 5\nSue 300: trees: 1, perfumes: 7, cars: 7\nSue 301: cars: 9, vizslas: 1, perfumes: 3\nSue 302: perfumes: 9, vizslas: 1, akitas: 5\nSue 303: akitas: 9, trees: 1, goldfish: 10\nSue 304: children: 10, vizslas: 6, pomeranians: 8\nSue 305: trees: 3, goldfish: 6, cats: 9\nSue 306: cars: 5, perfumes: 9, vizslas: 5\nSue 307: children: 0, goldfish: 7, trees: 2\nSue 308: trees: 9, samoyeds: 4, cars: 0\nSue 309: cats: 8, vizslas: 2, perfumes: 3\nSue 310: cars: 6, pomeranians: 6, vizslas: 6\nSue 311: vizslas: 6, akitas: 7, cats: 10\nSue 312: trees: 0, goldfish: 7, cars: 0\nSue 313: perfumes: 5, akitas: 5, cars: 2\nSue 314: akitas: 10, vizslas: 3, samoyeds: 8\nSue 315: cars: 3, perfumes: 1, goldfish: 8\nSue 316: pomeranians: 6, goldfish: 9, perfumes: 1\nSue 317: goldfish: 4, akitas: 6, cars: 2\nSue 318: perfumes: 8, vizslas: 8, akitas: 0\nSue 319: akitas: 10, cars: 5, vizslas: 6\nSue 320: vizslas: 4, akitas: 3, cats: 4\nSue 321: goldfish: 4, akitas: 8, cars: 8\nSue 322: pomeranians: 5, vizslas: 7, cats: 1\nSue 323: perfumes: 1, trees: 6, goldfish: 0\nSue 324: goldfish: 6, trees: 10, cars: 10\nSue 325: akitas: 2, samoyeds: 6, trees: 9\nSue 326: vizslas: 4, akitas: 7, cars: 9\nSue 327: children: 3, perfumes: 4, cars: 1\nSue 328: akitas: 9, perfumes: 6, cars: 10\nSue 329: perfumes: 2, goldfish: 0, trees: 1\nSue 330: vizslas: 10, pomeranians: 7, goldfish: 6\nSue 331: trees: 3, vizslas: 8, cars: 3\nSue 332: akitas: 2, cats: 1, goldfish: 8\nSue 333: cars: 6, trees: 2, vizslas: 0\nSue 334: samoyeds: 7, cars: 7, trees: 3\nSue 335: cats: 7, children: 1, perfumes: 8\nSue 336: akitas: 5, goldfish: 10, vizslas: 5\nSue 337: cats: 3, vizslas: 0, akitas: 10\nSue 338: perfumes: 8, cars: 1, trees: 8\nSue 339: cars: 4, samoyeds: 8, children: 2\nSue 340: goldfish: 9, pomeranians: 1, samoyeds: 1\nSue 341: akitas: 3, trees: 0, goldfish: 2\nSue 342: perfumes: 4, vizslas: 8, pomeranians: 9\nSue 343: akitas: 4, cars: 5, goldfish: 4\nSue 344: samoyeds: 5, cats: 4, trees: 0\nSue 345: samoyeds: 4, cars: 8, akitas: 2\nSue 346: akitas: 3, vizslas: 10, perfumes: 10\nSue 347: goldfish: 10, akitas: 4, cars: 1\nSue 348: perfumes: 10, cats: 4, vizslas: 5\nSue 349: akitas: 2, vizslas: 4, cars: 7\nSue 350: akitas: 5, vizslas: 5, cars: 6\nSue 351: vizslas: 8, perfumes: 6, cars: 3\nSue 352: cars: 10, vizslas: 0, goldfish: 10\nSue 353: cars: 10, perfumes: 5, children: 7\nSue 354: vizslas: 6, akitas: 3, samoyeds: 9\nSue 355: akitas: 2, perfumes: 7, cars: 10\nSue 356: cars: 10, perfumes: 7, children: 6\nSue 357: akitas: 4, cars: 8, trees: 1\nSue 358: trees: 2, cars: 1, goldfish: 2\nSue 359: vizslas: 5, cars: 9, trees: 4\nSue 360: perfumes: 4, akitas: 3, cars: 3\nSue 361: children: 3, akitas: 2, cats: 5\nSue 362: cars: 8, cats: 4, akitas: 10\nSue 363: cats: 2, trees: 1, vizslas: 4\nSue 364: vizslas: 2, pomeranians: 5, samoyeds: 9\nSue 365: samoyeds: 2, akitas: 7, goldfish: 9\nSue 366: goldfish: 8, trees: 7, cats: 2\nSue 367: perfumes: 2, vizslas: 6, trees: 5\nSue 368: cars: 5, samoyeds: 0, perfumes: 6\nSue 369: samoyeds: 10, trees: 10, vizslas: 1\nSue 370: trees: 2, vizslas: 3, cars: 4\nSue 371: akitas: 6, pomeranians: 2, cats: 4\nSue 372: trees: 2, perfumes: 3, goldfish: 9\nSue 373: vizslas: 5, children: 0, pomeranians: 6\nSue 374: trees: 1, vizslas: 8, perfumes: 10\nSue 375: cars: 0, akitas: 6, children: 0\nSue 376: akitas: 1, vizslas: 0, trees: 0\nSue 377: samoyeds: 10, cats: 5, pomeranians: 0\nSue 378: goldfish: 3, pomeranians: 7, cats: 7\nSue 379: perfumes: 0, cats: 0, trees: 8\nSue 380: perfumes: 4, samoyeds: 1, akitas: 7\nSue 381: akitas: 4, pomeranians: 2, children: 4\nSue 382: vizslas: 9, akitas: 4, trees: 10\nSue 383: trees: 1, vizslas: 10, akitas: 6\nSue 384: trees: 3, akitas: 8, goldfish: 3\nSue 385: goldfish: 6, perfumes: 2, children: 9\nSue 386: children: 10, akitas: 7, goldfish: 7\nSue 387: goldfish: 3, vizslas: 10, perfumes: 5\nSue 388: children: 4, trees: 0, cars: 2\nSue 389: trees: 0, cats: 3, goldfish: 10\nSue 390: samoyeds: 9, pomeranians: 0, cats: 6\nSue 391: samoyeds: 10, trees: 3, akitas: 4\nSue 392: akitas: 9, goldfish: 10, perfumes: 7\nSue 393: goldfish: 6, cars: 2, akitas: 9\nSue 394: trees: 4, goldfish: 9, vizslas: 7\nSue 395: vizslas: 4, samoyeds: 1, goldfish: 6\nSue 396: vizslas: 5, cats: 0, samoyeds: 1\nSue 397: goldfish: 7, cats: 0, trees: 7\nSue 398: cars: 10, akitas: 1, vizslas: 7\nSue 399: samoyeds: 10, cats: 6, goldfish: 6\nSue 400: cats: 6, samoyeds: 0, trees: 2\nSue 401: trees: 1, children: 4, goldfish: 2\nSue 402: cats: 8, vizslas: 4, children: 3\nSue 403: cars: 9, perfumes: 8, pomeranians: 2\nSue 404: goldfish: 8, trees: 2, cars: 5\nSue 405: perfumes: 1, pomeranians: 5, vizslas: 5\nSue 406: perfumes: 6, trees: 2, pomeranians: 6\nSue 407: trees: 0, goldfish: 6, cars: 6\nSue 408: trees: 0, samoyeds: 7, goldfish: 9\nSue 409: samoyeds: 10, goldfish: 6, pomeranians: 0\nSue 410: perfumes: 5, vizslas: 6, trees: 0\nSue 411: goldfish: 2, trees: 2, pomeranians: 0\nSue 412: pomeranians: 4, perfumes: 8, cats: 8\nSue 413: vizslas: 4, cars: 5, akitas: 1\nSue 414: perfumes: 2, trees: 8, goldfish: 7\nSue 415: akitas: 3, trees: 1, perfumes: 3\nSue 416: cars: 7, trees: 1, perfumes: 8\nSue 417: cars: 5, goldfish: 5, trees: 1\nSue 418: cars: 9, goldfish: 4, samoyeds: 2\nSue 419: pomeranians: 8, akitas: 1, goldfish: 6\nSue 420: cars: 0, cats: 0, children: 8\nSue 421: akitas: 10, goldfish: 1, vizslas: 8\nSue 422: children: 8, vizslas: 6, samoyeds: 10\nSue 423: samoyeds: 3, goldfish: 10, vizslas: 8\nSue 424: cars: 3, children: 7, goldfish: 4\nSue 425: cars: 9, perfumes: 9, goldfish: 8\nSue 426: akitas: 5, trees: 10, vizslas: 10\nSue 427: vizslas: 10, cars: 3, akitas: 7\nSue 428: cats: 6, perfumes: 5, goldfish: 10\nSue 429: goldfish: 7, trees: 5, vizslas: 10\nSue 430: perfumes: 3, trees: 7, cars: 3\nSue 431: cars: 2, vizslas: 1, akitas: 6\nSue 432: pomeranians: 8, perfumes: 5, cars: 3\nSue 433: children: 8, cars: 0, perfumes: 7\nSue 434: samoyeds: 0, vizslas: 9, akitas: 10\nSue 435: akitas: 3, vizslas: 8, cats: 4\nSue 436: goldfish: 5, trees: 8, samoyeds: 8\nSue 437: cars: 10, samoyeds: 9, goldfish: 7\nSue 438: samoyeds: 5, akitas: 7, perfumes: 9\nSue 439: goldfish: 10, perfumes: 5, cars: 0\nSue 440: pomeranians: 1, samoyeds: 9, children: 4\nSue 441: vizslas: 4, perfumes: 2, cats: 5\nSue 442: trees: 0, pomeranians: 3, cars: 7\nSue 443: akitas: 0, cars: 2, vizslas: 10\nSue 444: children: 1, akitas: 9, trees: 0\nSue 445: cars: 5, perfumes: 7, goldfish: 9\nSue 446: akitas: 0, perfumes: 1, vizslas: 2\nSue 447: vizslas: 7, perfumes: 0, cars: 5\nSue 448: vizslas: 6, goldfish: 10, trees: 0\nSue 449: cars: 7, vizslas: 7, trees: 3\nSue 450: pomeranians: 4, akitas: 4, vizslas: 8\nSue 451: cats: 4, perfumes: 8, children: 3\nSue 452: samoyeds: 8, akitas: 9, cars: 1\nSue 453: cars: 8, akitas: 5, vizslas: 2\nSue 454: vizslas: 9, perfumes: 4, akitas: 4\nSue 455: akitas: 3, goldfish: 2, vizslas: 6\nSue 456: cars: 4, perfumes: 5, goldfish: 10\nSue 457: trees: 9, pomeranians: 4, goldfish: 10\nSue 458: pomeranians: 1, perfumes: 9, children: 6\nSue 459: samoyeds: 0, goldfish: 8, vizslas: 6\nSue 460: cars: 10, goldfish: 8, samoyeds: 8\nSue 461: akitas: 8, goldfish: 9, vizslas: 2\nSue 462: cars: 1, vizslas: 2, akitas: 8\nSue 463: goldfish: 2, akitas: 4, samoyeds: 10\nSue 464: children: 5, perfumes: 5, cars: 5\nSue 465: perfumes: 9, trees: 0, samoyeds: 6\nSue 466: akitas: 5, goldfish: 3, cats: 6\nSue 467: perfumes: 3, goldfish: 0, trees: 4\nSue 468: goldfish: 2, children: 4, trees: 1\nSue 469: cars: 0, perfumes: 8, children: 7\nSue 470: vizslas: 8, cats: 5, samoyeds: 9\nSue 471: pomeranians: 7, trees: 2, goldfish: 3\nSue 472: goldfish: 8, akitas: 4, perfumes: 5\nSue 473: perfumes: 2, pomeranians: 3, cars: 8\nSue 474: samoyeds: 0, akitas: 7, pomeranians: 6\nSue 475: vizslas: 7, perfumes: 1, trees: 6\nSue 476: vizslas: 3, samoyeds: 1, perfumes: 10\nSue 477: cars: 6, perfumes: 5, vizslas: 2\nSue 478: pomeranians: 1, goldfish: 3, akitas: 7\nSue 479: goldfish: 10, trees: 0, cars: 3\nSue 480: cats: 3, akitas: 5, vizslas: 8\nSue 481: pomeranians: 5, vizslas: 2, trees: 3\nSue 482: cars: 8, samoyeds: 10, goldfish: 10\nSue 483: pomeranians: 3, vizslas: 6, goldfish: 5\nSue 484: perfumes: 7, vizslas: 4, akitas: 7\nSue 485: goldfish: 1, trees: 0, perfumes: 10\nSue 486: goldfish: 6, perfumes: 0, akitas: 10\nSue 487: cats: 2, akitas: 10, trees: 1\nSue 488: akitas: 1, goldfish: 3, cars: 7\nSue 489: goldfish: 3, akitas: 6, vizslas: 6\nSue 490: goldfish: 8, perfumes: 2, akitas: 2\nSue 491: trees: 4, vizslas: 8, perfumes: 6\nSue 492: cars: 9, perfumes: 3, cats: 0\nSue 493: trees: 3, vizslas: 6, goldfish: 7\nSue 494: trees: 8, samoyeds: 1, perfumes: 5\nSue 495: children: 9, akitas: 8, vizslas: 4\nSue 496: vizslas: 2, pomeranians: 1, perfumes: 7\nSue 497: trees: 2, akitas: 4, vizslas: 6\nSue 498: akitas: 8, pomeranians: 7, trees: 0\nSue 499: perfumes: 6, goldfish: 3, vizslas: 7\nSue 500: cars: 1, perfumes: 6, vizslas: 1")
(def report "children: 3\ncats: 7\nsamoyeds: 2\npomeranians: 3\nakitas: 0\nvizslas: 0\ngoldfish: 5\ntrees: 3\ncars: 2\nperfumes: 1")


(defn split-pair [s]
  (-> s
    (str/split #": ")
    (update 1 edn/read-string)))

(defn parse-report [lines]
  (->> lines
    (str/split-lines)
    (map split-pair)
    (into {})))

(defn parse-line [line]
  (let [[id stats] (str/split line #": " 2)
        pairs      (str/split stats #", ")
        m          (->> pairs
                     (map split-pair)
                     (into {}))]
    [id m]))

(def parsed-report (parse-report report))


(defn f1 [input]
  (let [match? (fn match? [[id y]]
                 (when (= y (select-keys parsed-report (keys y)))
                   id))]
    (->> input
      (str/split-lines)
      (map parse-line)
      (some match?))))


(defn f2 [input]
  (let [ops    {"cats"        >
                "trees"       >
                "pomeranians" <
                "goldfish"    <}
        match? (fn [[id m]]
                 (reduce-kv
                   (fn [id k v]
                     (let [predicate (get ops k =)]
                       (if (predicate v (get parsed-report k))
                         id
                         (reduced nil))))
                   id, m))]
    (->> input
      (str/split-lines)
      (map parse-line)
      (some match?))))


(assert (= (f1 input) "Sue 213"))
(assert (= (f2 input) "Sue 323"))