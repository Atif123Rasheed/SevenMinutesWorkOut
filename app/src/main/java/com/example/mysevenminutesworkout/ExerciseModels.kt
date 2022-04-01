package com.example.mysevenminutesworkout

data class ExerciseModels (
    private var id: Int,
    private var exerciseName: String,
    private var exerciseImage: Int,
    private var isExerciseSelected: Boolean,
    private var isExerciseCompleted:Boolean
){
    fun getId(): Int{
        return id
    }
    fun setId(id:Int){
        this.id = id
    }
    fun getExerciseName(): String{
        return exerciseName
    }
    fun setExerciseName(exerciseName: String){
        this.exerciseName = exerciseName
    }
    fun getExerciseImage(): Int{
        return exerciseImage
    }
    fun setExerciseImage(exerciseImage: Int){
        this.exerciseImage = exerciseImage
    }
    fun getIsExerciseSelected(): Boolean{
        return isExerciseSelected
    }
    fun setIsExerciseSelected(isExerciseSelected: Boolean){
        this.isExerciseSelected = isExerciseSelected
    }

    fun getIsExerciseCompleted(): Boolean{
        return isExerciseCompleted
    }
    fun setIsExerciseCompleted(isExerciseCompleted: Boolean){
        this.isExerciseCompleted = isExerciseCompleted
    }
}