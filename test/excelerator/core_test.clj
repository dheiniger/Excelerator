(ns excelerator.core-test
  (:require [clojure.test :refer :all]
            [excelerator.core :refer :all]))

(deftest empty-string
  (testing "An empty string"
    (is (= 0.0 (cell+ "")))))

(deftest simple-string
  (testing "A simple numeric string"
    (is (= 1.0 (cell+ "1")))))

(deftest string-with-many-numeric-values
  (testing "A string with many numbers, but no new lines"
    (is (thrown? NumberFormatException (cell+ "1 2 3 4 5 6 7 8 9 10")))))

(deftest string-with-multiple-lines
  (testing "A string with many numbers, each on its own line"
    (is (= 55.0 (cell+ "1\n 2\n 3\n 4\n 5\n 6\n 7\n 8\n 9\n 10")))))

(deftest string-with-multiple-lines-with-non-numeric-values
  (testing "A string with many numbers and 1 non-numeric character, each on its own line"
    (is (thrown? NumberFormatException (cell+ "a\n1\n 2\n 3\n 4\n 5\n 6\n 7\n 8\n 9\n 10")))))

(deftest string-with-multiple-lines-with-1-blank-line
  (testing "A string with many numbers but a single blank line"
    (is (= 55.0 (cell+ "1\n\n 2\n 3\n 4\n 5\n 6\n 7\n 8\n 9\n 10")))))

(deftest string-with-multiple-lines-with-2-blank-lines
  (testing "A string with many numbers but a single blank line"
    (is (= 55.0 (cell+ "1\n\n\n 2\n 3\n 4\n 5\n 6\n 7\n 8\n 9\n 10")))))