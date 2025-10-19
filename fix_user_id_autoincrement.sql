-- SQL Script to Fix User ID Auto-Increment in PostgreSQL
-- This script adds proper sequence-based auto-increment to the app_user.id column

-- Step 1: Temporarily drop the primary key constraint
ALTER TABLE app_user DROP CONSTRAINT IF EXISTS app_user_pkey;

-- Step 2: Make id column nullable temporarily
ALTER TABLE app_user ALTER COLUMN id DROP NOT NULL;

-- Step 3: Drop any existing default
ALTER TABLE app_user ALTER COLUMN id DROP DEFAULT;

-- Step 4: Create a sequence for auto-incrementing IDs
CREATE SEQUENCE IF NOT EXISTS app_user_id_seq;

-- Step 5: Set the sequence as the default value for the id column
ALTER TABLE app_user ALTER COLUMN id SET DEFAULT nextval('app_user_id_seq');

-- Step 6: Make id column NOT NULL again
ALTER TABLE app_user ALTER COLUMN id SET NOT NULL;

-- Step 7: Make the sequence owned by the id column (so it's deleted if the column is deleted)
ALTER SEQUENCE app_user_id_seq OWNED BY app_user.id;

-- Step 8: Re-add the primary key constraint
ALTER TABLE app_user ADD PRIMARY KEY (id);

-- Step 9: Set the sequence to start from the next available ID
-- If you have existing users, this will set it to max(id) + 1
-- If no users exist, it will start from 1
SELECT setval('app_user_id_seq', COALESCE((SELECT MAX(id) FROM app_user), 0) + 1, false);

-- Verify the changes
SELECT column_name, column_default, is_nullable, data_type
FROM information_schema.columns
WHERE table_name = 'app_user' AND column_name = 'id';
