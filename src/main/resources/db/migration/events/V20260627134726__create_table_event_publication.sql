CREATE TABLE event_publication (
  id UUID NOT NULL,
  completion_attempts INTEGER NOT NULL,
  completion_date TIMESTAMP WITH TIME ZONE,
  event_type TEXT NOT NULL,
  last_resubmission_date TIMESTAMP WITH TIME ZONE,
  listener_id TEXT NOT NULL,
  publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
  serialized_event TEXT NOT NULL,
  status VARCHAR(255) NOT NULL,
  CONSTRAINT pk_event_publication PRIMARY KEY (id)
);


CREATE INDEX IF NOT EXISTS idx_event_pub_completion_date
ON event_publication (completion_date) WHERE completion_date IS NULL;
