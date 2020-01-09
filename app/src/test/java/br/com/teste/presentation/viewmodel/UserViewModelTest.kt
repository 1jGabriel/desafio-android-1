package br.com.teste.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.teste.RxImmediateSchedulerRule
import br.com.teste.data.model.User
import br.com.teste.data.remote.GitHubService
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Mock
    private lateinit var api: GitHubService

    lateinit var usersViewModel: UsersViewModel

    val respostaEsperada = arrayListOf(User("www", 1, "1jgabriel"))
    val erroEsperado = "Houve um problema ao consultar o servidor. Verifique sua conexão"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        usersViewModel = UsersViewModel()
        usersViewModel.service = api
    }

    @Test
    fun `requisicao retornando usuarios`() {
        assertEquals(usersViewModel.users.value, null)

        //Mockar resposta do servidor
        Mockito.`when`(api.getUsers()).thenReturn(Observable.just(Response.success(respostaEsperada)))

        usersViewModel.getUsers()
        assertEquals(respostaEsperada, usersViewModel.users.value )
        assertEquals(false, usersViewModel.status.value)
        assertEquals(null, usersViewModel.error.value)
    }


    @Test
    fun `requisicao retornando erro`() {

        assertEquals(null, usersViewModel.users.value)

        Mockito.`when`(api.getUsers()).thenReturn(Observable
                .error(Throwable(erroEsperado, Throwable())))

        usersViewModel.getUsers()

        assertEquals(null, usersViewModel.users.value)
        assertEquals(false, usersViewModel.status.value)
        assertEquals(erroEsperado, usersViewModel.error.value)
    }
}