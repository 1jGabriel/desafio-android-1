package br.com.teste.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.teste.RxImmediateSchedulerRule
import br.com.teste.data.model.DetailUser
import br.com.teste.data.remote.GitHubService
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DetailUserViewModelTest {

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

    lateinit var viewModel: DetailUserViewModel

    val detailUser = DetailUser("www", "Um jovem programador",
            "GitHub",
            Date(), Date(), "github@github",
            "322", "322", "Salvador Ba",
            "Joao", "2", "322")
    val idUser = "2"
    val erroEsperado = "Houve um problema ao consultar o servidor. Verifique sua conexão"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailUserViewModel()
        viewModel.service = api
    }

    @Test
    fun `requisicao retornar valores`() {
        assertEquals( null, viewModel.user.value)
        Mockito.`when`(api.getUserDetail(idUser)).thenReturn(Observable
                .just(Response.success(detailUser)))

        viewModel.getUserDetail(idUser)
        assertEquals(detailUser, viewModel.user.value)
        assertEquals(false, viewModel.status.value)
        assertEquals(null, viewModel.error.value)
    }


    @Test
    fun `requisicao retornando erro`() {
        assertEquals( null, viewModel.user.value)

        Mockito.`when`(api.getUserDetail(idUser)).thenReturn(Observable
                .error(Throwable(erroEsperado, Throwable())))
        viewModel.getUserDetail(idUser)

        assertEquals(null, viewModel.user.value)
        assertEquals(false, viewModel.status.value)
        Assert.assertNotEquals(null, viewModel.error.value)
    }
}