package com.example.mysevenminutesworkout

object Constants {
    fun defaultExerciseList():ArrayList<ExerciseModels>{

        val exerciseList = ArrayList<ExerciseModels>()

        val barbell = ExerciseModels(
            1,
            "BarBel",
            R.drawable.image_barbell,
            isExerciseSelected = false,
            isExerciseCompleted = false
        )
        exerciseList.add(barbell)

        val dumbbell = ExerciseModels(
            2,
            "Dumb Bell",
            R.drawable.image_dumb_bell,
            isExerciseSelected = false,
            isExerciseCompleted = false
        )
        exerciseList.add(dumbbell)

        val jumpingJack = ExerciseModels(
            3,
            "Jumping Jacks",
            R.drawable.image_jumping_jack,
            isExerciseSelected = false,
            isExerciseCompleted = false
        )
        exerciseList.add(jumpingJack)

        val planks = ExerciseModels(
            4,
            "Plank",
            R.drawable.image_plunk,
            isExerciseSelected = false,
            isExerciseCompleted = false
        )
        exerciseList.add(planks)

        val pullUps = ExerciseModels(
            5,
            "Pull Ups",
            R.drawable.image_pull_up,
            isExerciseSelected = false,
            isExerciseCompleted = false
        )
        exerciseList.add(pullUps)

        val pushUps = ExerciseModels(
            6,
            "Push Ups",
            R.drawable.image_push_ups,
            isExerciseSelected = false,
            isExerciseCompleted = false
        )
        exerciseList.add(pushUps)

        val sitUps = ExerciseModels(
            7,
            "Sit Ups",
            R.drawable.image_sit_ups,
            isExerciseSelected = false,
            isExerciseCompleted = false
        )
        exerciseList.add(sitUps)

        val standingForwardBend = ExerciseModels(
            8,
            "Standing Forward Bands",
            R.drawable.image_standing_forward_bend,
            isExerciseSelected = false,
            isExerciseCompleted = false
        )
        exerciseList.add(standingForwardBend)

        val treadmill = ExerciseModels(
            9,
            "Treadmill",
            R.drawable.image_treadmill,
            isExerciseSelected = false,
            isExerciseCompleted = false
        )
        exerciseList.add(treadmill)

        val weightPlate = ExerciseModels(
            10,
            "Weight Plate",
            R.drawable.image_weight_plate,
            isExerciseSelected = false,
            isExerciseCompleted = false
        )
        exerciseList.add(weightPlate)

        return exerciseList
    }
}