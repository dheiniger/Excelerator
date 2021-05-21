(ns excelerator.core-test
  (:require [clojure.test :refer :all]
            [excelerator.core :refer :all]))

(deftest empty-string
  (testing "An empty string"
    (is (= "" (cell+ "")))))

(deftest simple-string
  (testing "A simple numeric string"
    (is (= "1.00" (cell+ "1")))))

(deftest string-with-many-numeric-values
  (testing "A string with many numbers, but no new lines"
    (is (thrown? NumberFormatException  (cell+ "1 2 3 4 5 6 7 8 9 10")))))

(deftest string-with-multiple-lines
  (testing "A string with many numbers, each on its own line"
    (is (= "1.00\n2.00\n3.00\n4.00\n5.00\n6.00" (cell+ "1\n 2\n 3\n 4\n 5\n 6")))))

(deftest string-with-multiple-lines-with-non-numeric-values
  (testing "A string with many numbers and 1 non-numeric character, each on its own line"
    (is (thrown? NumberFormatException (cell+ "a\n1\n 2\n 3\n 4\n 5\n 6\n 7\n 8\n 9\n 10")))))

(deftest string-with-multiple-lines-with-1-blank-line
  (testing "A string with many numbers but a single blank line"
    (is (= "1.00\n\n2.00\n3.00\n4.00\n5.00\n6.00" (cell+ "1\n\n 2\n 3\n 4\n 5\n 6")))))

(deftest string-with-multiple-lines-with-2-blank-lines
  (testing "A string with many numbers but a single blank line"
    (is (= "1.00\n\n\n2.00\n3.00\n4.00\n5.00\n6.00" (cell+ "1\n\n\n 2\n 3\n 4\n 5\n 6\n")))))

(deftest multiple-strings-delimited-by-quotes
  (testing "A string that contains multiple strings, delimited by \""
    (is (= "286.47\n85.00\n16.00\n79.05\n24.00\n16.00\n317.02\n28.95\n440.15" (cell+ "\"7.60   \n64.00   \n24.00   \n0.00   \n0.00   \n0.00   \n79.18   \n74.24   \n0.00   \n37.45\"\n\"50.00   \n0.00   \n0.00   \n35.00\"\n16.00\n\"10.00   \n9.10   \n59.95\"\n\"24.00   \n0.00\"\n16.00\n\"9.10   \n50.00   \n51.10   \n34.95   \n25.00   \n86.87   \n15.00   \n45.00\"\n\"9.95   \n0.00   \n19.00\"\n\"100.00   \n20.00   \n60.00   \n0.00   \n25.00   \n65.00   \n60.00   \n25.00   \n33.15   \n15.00   \n25.00   \n12.00   \n0.00\"")))))