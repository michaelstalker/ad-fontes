CREATE TABLE IF NOT EXISTS verses(
  id SERIAL PRIMARY KEY,
  book_id BIGINT,
  chapter BIGINT,
  verse BIGINT,
  part_of_speech varchar(40),
  person varchar(3),
  tense varchar(15),
  voice varchar(10),
  mood varchar(15),
  word_case varchar(15),
  number_form varchar(15),
  gender varchar(10),
  degree varchar(15),
  word varchar(30),
  word_no_punctuation varchar(30),
  normalized_word varchar(30),
  lemma varchar(30)
);
